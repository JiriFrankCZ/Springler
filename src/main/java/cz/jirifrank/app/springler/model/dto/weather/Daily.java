package cz.jirifrank.app.springler.model.dto.weather;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
		"summary",
		"icon",
		"data"
})
public class Daily {

	@JsonProperty("summary")
	private String summary;
	@JsonProperty("icon")
	private String icon;
	@JsonProperty("data")
	private List<Data> data = null;

	@JsonProperty("summary")
	public String getSummary() {
		return summary;
	}

	@JsonProperty("summary")
	public void setSummary(String summary) {
		this.summary = summary;
	}

	@JsonProperty("icon")
	public String getIcon() {
		return icon;
	}

	@JsonProperty("icon")
	public void setIcon(String icon) {
		this.icon = icon;
	}

	@JsonProperty("data")
	public List<Data> getData() {
		return data;
	}

	@JsonProperty("data")
	public void setData(List<Data> data) {
		this.data = data;
	}
}