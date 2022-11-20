package cloud.nino.nino.domain.elasticsearch;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by dawit on 20/04/2018.
 */
public class ElasticsearchModel implements Serializable {


    protected String _id;
    protected float score;

    public ElasticsearchModel() {
    }

    public ElasticsearchModel(String _id) {
        this._id = _id;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof ElasticsearchModel)) {
            return false;
        }
        ElasticsearchModel model = (ElasticsearchModel) o;
        return StringUtils.equals(_id, model._id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id);
    }
}
