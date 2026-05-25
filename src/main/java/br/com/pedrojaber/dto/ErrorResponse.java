package br.com.pedrojaber.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private String mensagem;
    private List<String> errors;
}
