package cloud.nino.nino.service.custom;

import cloud.nino.nino.domain.Image;
import cloud.nino.nino.domain.enumeration.EntityType;
import cloud.nino.nino.repository.ImageRepository;
import cloud.nino.nino.service.UserService;
import cloud.nino.nino.service.dto.ImageDTO;
import cloud.nino.nino.service.mapper.ImageMapper;
import cloud.nino.nino.web.rest.errors.BadRequestAlertException;
import cloud.nino.nino.web.rest.errors.CustomParameterizedException;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Image.
 */
@Service
@Transactional
public class ImageCustomService {

    private final Logger log = LoggerFactory.getLogger(ImageCustomService.class);

    private final ImageRepository imageRepository;

    private final ImageMapper imageMapper;

    private final UserService userService;

    @Value("${nino.images.location}")
    private String imageLocation;

    @Value("${nino.images.thumbnail-size}")
    private int thumbnailSize;

    public ImageCustomService(ImageRepository imageRepository,
                              ImageMapper imageMapper, UserService userService) {
        this.imageRepository = imageRepository;
        this.imageMapper = imageMapper;
        this.userService = userService;
    }

    /**
     * Save a image.
     *
     * @param imageDTO the entity to save
     * @return the persisted entity
     */
    public ImageDTO save(ImageDTO imageDTO) {
        log.debug("Request to save Image : {}", imageDTO);
        Image image = imageMapper.toEntity(imageDTO);
        image = imageRepository.save(image);
        return imageMapper.toDto(image);
    }

    /**
     * Get all the images.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ImageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Images");
        return imageRepository.findAll(pageable).stream()
            .map(imageMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one image by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ImageDTO> findOne(Long id) {
        log.debug("Request to get Image : {}", id);
        return imageRepository.findById(id)
            .map(imageMapper::toDto);
    }

    /**
     * Delete the image by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Image : {}", id);
        Image image = imageRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        try {
            Path imageFilePath = Paths.get(image.getLocation() + "/" + image.getImagePath());
            Path thumbnailFilePath = Paths.get(image.getLocation() + "/" + image.getThumbnailPath());
            Files.delete(imageFilePath);
            Files.delete(thumbnailFilePath);
        } catch (IOException ioException) {
            log.error("Cannot delete image with id " + image.getId(), ioException);
        }
        imageRepository.deleteById(id);
    }

    /**
     * Upload, save and create thumbnail of images
     *
     * @param file
     * @param entityType
     * @param id         @return
     * @throws BadRequestAlertException
     */
    public ImageDTO upload(MultipartFile file, EntityType entityType, Long id, String imageName) throws BadRequestAlertException {
        MimeTypes allTypes = MimeTypes.getDefaultMimeTypes();
        MimeType format = null;
        try {
            format = allTypes.forName(file.getContentType());
        } catch (MimeTypeException e) {
            new BadRequestAlertException("Formato file non riconoscito", "Image", "0");
        }

        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setCreateDate(ZonedDateTime.now());
        imageDTO.setModifiedDate(ZonedDateTime.now());
        imageDTO.setFormat(format.getExtension());
        imageDTO.setLocation(imageLocation);
        imageDTO.setName(file.getOriginalFilename().replace(format.getExtension(), StringUtils.EMPTY));
        userService.getCurrentUser().map(user -> {
            imageDTO.setCratedById(user.getId());
            return user;
        });
        if (entityType == EntityType.ALBERO) {
            imageDTO.setAlberoId(id);
        }
        try {
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            String fileName = StringUtils.isBlank(imageName) ? file.getOriginalFilename() : imageName;
            String pathString = imageDTO.getCreateDate().toEpochSecond() + "_" + fileName;
            Path path = Paths.get(applayImageFolder(pathString));
            Files.write(path, bytes);
            imageDTO.setImagePath(pathString);

            //Create thumbnail
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            if (bufferedImage.getWidth() > this.thumbnailSize || bufferedImage.getHeight() > this.thumbnailSize) {
                int newWidth;
                int newHeight;
                if (bufferedImage.getWidth() > bufferedImage.getHeight()) { // let's maintain width/height ratio resizing proportionally
                    newWidth = this.thumbnailSize;
                    newHeight = (this.thumbnailSize * bufferedImage.getHeight()) / bufferedImage.getWidth();

                } else {
                    newHeight = this.thumbnailSize;
                    newWidth = (this.thumbnailSize * bufferedImage.getWidth()) / bufferedImage.getHeight();

                }
                BufferedImage thumbnail = resize(bufferedImage, newHeight, newWidth);
                String thumbnailPathString = imageDTO.getCreateDate().toEpochSecond() + "_thumbnail_" + fileName;
                Path thumbnailPath = Paths.get(applayImageFolder(thumbnailPathString));
                Files.write(thumbnailPath, toByteArrayAutoClosable(thumbnail, FilenameUtils.getExtension(file.getOriginalFilename())));
                imageDTO.setThumbnailPath(thumbnailPathString);
            }

            Image image = this.imageRepository.save(imageMapper.toEntity(imageDTO));
            return imageMapper.toDto(image);
        } catch (IOException e) {
            throw new RuntimeException("Non è stato possibile salvare il file, verificare i permessi di accesso alla cartella " + imageLocation);
        }

    }

    /**
     * Retrieve main image or thumbnail resource
     *
     * @param id
     * @param mainImage main image or thumbnail
     * @return
     */
    public Resource  getImageFile(Long id, boolean mainImage) {
        Optional<ImageDTO> imageDTO = imageRepository.findById(id)
            .map(imageMapper::toDto);
        if (!imageDTO.isPresent()) {
            throw new CustomParameterizedException("L'immage con l'id " + id + " non esiste");
        }
        Resource resource = new FileSystemResource(mainImage ? applayImageFolder(imageDTO.get().getImagePath()) : applayImageFolder(imageDTO.get().getThumbnailPath()));
        return resource;
    }

    private BufferedImage resize(BufferedImage img, int height, int width) {

        java.awt.Image tmp = img.getScaledInstance(width, height, java.awt.Image.SCALE_DEFAULT);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

    private byte[] toByteArrayAutoClosable(BufferedImage image, String type) throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            ImageIO.write(image, type, out);
            return out.toByteArray();
        }
    }

    private String applayImageFolder(String relativePath) {
        return imageLocation + "/" + relativePath;
    }

}
