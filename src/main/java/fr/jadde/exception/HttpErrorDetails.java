package fr.jadde.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public record HttpErrorDetails(String errorKey, Map<String, String> parameters) {

}
