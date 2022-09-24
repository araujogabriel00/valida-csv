package com.example.sincronizacaoreceita.entities;

import com.example.sincronizacaoreceita.dto.ReceitaSyncDTO;
import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class ReceitaSync {

  @CsvBindByName String agencia;

  @CsvBindByName String conta;

  @CsvBindByName String saldo;

  @CsvBindByName String status;

  public static ReceitaSyncDTO.ReceitaSyncDTOBuilder fromEntity(
      ReceitaSync receitaSync, boolean valida) {
    return ReceitaSyncDTO.builder()
        .agencia(receitaSync.getAgencia())
        .conta(receitaSync.getConta())
        .saldo(receitaSync.getSaldo())
        .status(receitaSync.getStatus())
        .valida(valida);
  }
}
