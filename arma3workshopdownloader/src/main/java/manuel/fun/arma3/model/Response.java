
package manuel.fun.arma3.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "result",
    "resultcount",
    "publishedfiledetails"
})
@ToString
public class Response implements Serializable
{

    @JsonProperty("result")
    private Long result;
    @JsonProperty("resultcount")
    private Long resultcount;
    @JsonProperty("publishedfiledetails")
    private List<Publishedfiledetail> publishedfiledetails = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -5710207746886827207L;

    @JsonProperty("result")
    public Long getResult() {
        return result;
    }

    @JsonProperty("result")
    public void setResult(Long result) {
        this.result = result;
    }

    @JsonProperty("resultcount")
    public Long getResultcount() {
        return resultcount;
    }

    @JsonProperty("resultcount")
    public void setResultcount(Long resultcount) {
        this.resultcount = resultcount;
    }

    @JsonProperty("publishedfiledetails")
    public List<Publishedfiledetail> getPublishedfiledetails() {
        return publishedfiledetails;
    }

    @JsonProperty("publishedfiledetails")
    public void setPublishedfiledetails(List<Publishedfiledetail> publishedfiledetails) {
        this.publishedfiledetails = publishedfiledetails;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
