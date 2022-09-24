package com.example.sincronizacaoreceita.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
@Data
public class ReceitaSyncDTO {

  private String agencia;
  private String conta;
  private String saldo;
  private String status;
  private boolean valida;
}
