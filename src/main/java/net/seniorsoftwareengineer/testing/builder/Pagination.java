package net.seniorsoftwareengineer.testing.builder;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Pagination implements Serializable {
	@JsonProperty(value = "isIfiniteScroll")
	Boolean isInfiniteScroll;

	@Override
	public String toString() {
		return "Pagination [isInfiniteScroll=" + isInfiniteScroll + "]";
	}

}
