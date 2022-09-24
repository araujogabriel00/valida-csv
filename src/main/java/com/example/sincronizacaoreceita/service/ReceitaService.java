package com.example.sincronizacaoreceita.service;

import com.example.sincronizacaoreceita.entities.ReceitaSync;

import java.io.File;
import java.io.Writer;
import java.util.List;

public interface ReceitaService {

  boolean atualizarConta(String agencia, String conta, String status);

  void readCsv(File file);

  Writer getOutputPath();

  void writeCsv(List<ReceitaSync> inputData);
}
