package com.example.sincronizacaoreceita.service;

import com.example.sincronizacaoreceita.entities.ReceitaSync;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class ReceitaServiceImpl implements ReceitaService {

  @Autowired private static ReceitaSync receitaSync;

  private static Logger logger =
      java.util.logging.Logger.getLogger(String.valueOf(ReceitaServiceImpl.class));

  @Override
  public boolean atualizarConta(String agencia, String conta, String status) {

    // Formato agencia: 0000
    if (agencia == null || agencia.length() != 4) {
      return false;
    }

    // Formato conta: 000000
    if (conta == null || conta.length() != 6) {
      return false;
    }

    // Tipos de status validos:
    List tipos = new ArrayList();
    tipos.add("A");
    tipos.add("I");
    tipos.add("B");
    tipos.add("P");

    if (status == null || !tipos.contains(status)) {
      return false;
    }

    // Simula tempo de resposta do serviço (entre 1 e 5 segundos)
    long wait = Math.round(Math.random() * 4000) + 1000;

    try {
      Thread.sleep(wait);
    } catch (InterruptedException e) {
      logger.severe("Erro na thread" + e.getMessage());
    }

    // Simula cenario de erro no serviço (0,1% de erro)
    long randomError = Math.round(Math.random() * 1000);

    if (randomError == 500) {
      throw new RuntimeException("Erro na obtenção dos dados");
    }

    return true;
  }

  @Override
  public void readCsv(File file) {
    logger.info("Lendo Arquivo!!!");
    List<ReceitaSync> inputData = null;
    try {
      inputData =
          new CsvToBeanBuilder(new FileReader(file))
              .withSeparator(';')
              .withSkipLines(0)
              .withType(ReceitaSync.class)
              .build()
              .parse();
      if (!inputData.isEmpty()) {
        writeCsv(inputData);
      } else {
        logger.severe("Arquivo vazio");
      }
    } catch (FileNotFoundException e) {
      logger.severe("Arquivo não encontrado");
    }
  }

  @Override
  public Writer getOutputPath() {
    String outputPath = null;
    Writer writer = null;

    try {
      outputPath = new java.io.File(".").getCanonicalPath();
      writer = new FileWriter(outputPath + "\\retornoReceita.csv");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return writer;
  }

  @Override
  public void writeCsv(List<ReceitaSync> inputData) {

    Writer writer = getOutputPath();

    StatefulBeanToCsv beanToCsv =
        new StatefulBeanToCsvBuilder<ReceitaSync>(writer)
            .withSeparator(';')
            .withApplyQuotesToAll(false)
            .build();

    inputData.forEach(
        conta -> {
          boolean valida;
          valida =
              atualizarConta(
                  conta.getAgencia(), conta.getConta().replace("-", ""), conta.getStatus());
          try {
            beanToCsv.write(receitaSync.fromEntity(conta, valida));
          } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            throw new RuntimeException(e);
          }
        });

    try {
      writer.close();
      logger.info("Arquivo criado");
    } catch (IOException e) {
      throw new RuntimeException("Erro na escrita");
    }
  }
}
