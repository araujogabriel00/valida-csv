package com.example.sincronizacaoreceita.service;

import com.example.sincronizacaoreceita.entities.ReceitaSync;
import com.example.sincronizacaoreceita.factory.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

@ExtendWith(SpringExtension.class)
class ReceitaServiceImplTest {
  @Mock private ReceitaService receitaService;
  @InjectMocks ReceitaServiceImpl receitaServiceImpl;
  private String nonExistingAgency;
  private File nonExistingFile;
  private File emptyExistingFile;
  private String nonPresentStatus;
  private String nullStatus;
  private ReceitaSync receitaSync;
  private List<ReceitaSync> receitaSyncList;

  @BeforeEach
  void setUp() {

    nonPresentStatus = "G";
    nullStatus = null;
    nonExistingAgency = null;
    receitaSync = Factory.createConta();
    receitaSyncList = Factory.createContaList();
    nonExistingFile = new File("");
    emptyExistingFile = new File("");
    receitaServiceImpl = new ReceitaServiceImpl();

    Mockito.doThrow(FileNotFoundException.class).when(receitaService).readCsv(nonExistingFile);
    Mockito.doThrow(RuntimeException.class).when(receitaService).getOutputPath();
    Mockito.doThrow(RuntimeException.class).when(receitaService).writeCsv(receitaSyncList);
  }

  @Test
  void atulizarContaShouldReturnFalseWhenAgenciaDosentHaveFourDigits() {

    var conta =
        receitaServiceImpl.atualizarConta(
            receitaSync.getAgencia(), receitaSync.getConta(), receitaSync.getStatus());
    Assertions.assertFalse(conta);
  }

  @Test
  void atulizarContaShouldReturnFalseWhenAgenciaIsNull() {

    var conta =
        receitaServiceImpl.atualizarConta(
            nonExistingAgency, receitaSync.getConta(), receitaSync.getStatus());
    Assertions.assertFalse(conta);
  }

  @Test
  void atulizarContaShouldReturnFalseWhenContaIsNull() {

    var conta =
        receitaServiceImpl.atualizarConta(receitaSync.getConta(), null, receitaSync.getStatus());
    Assertions.assertFalse(conta);
  }

  @Test
  void atulizarContaShouldReturnFalseWhenAgenciaDosentHaveSixDigits() {

    var conta =
        receitaServiceImpl.atualizarConta(
            receitaSync.getAgencia(), receitaSync.getConta(), receitaSync.getStatus());
    Assertions.assertFalse(conta);
  }

  @Test
  void atulizarContaShouldReturnFalseWhenStatusIsNotPresent() {

    var conta =
        receitaServiceImpl.atualizarConta(
            receitaSync.getAgencia(), receitaSync.getConta(), nonPresentStatus);
    Assertions.assertFalse(conta);
  }

  @Test
  void atulizarContaShouldReturnTrue() {

    var conta =
        receitaServiceImpl.atualizarConta(
            receitaSync.getAgencia(),
            receitaSync.getConta().replace("-", ""),
            receitaSync.getStatus());

    Assertions.assertNotNull(conta);
    Assertions.assertTrue(conta);
  }

  @Test
  void atulizarContaShouldReturnFalseWhenStatusIsNull() {

    var conta =
        receitaServiceImpl.atualizarConta(
            receitaSync.getAgencia(), receitaSync.getConta(), nullStatus);
    Assertions.assertNotNull(conta);
    Assertions.assertFalse(conta);
  }

  @Test
  void readCSVShouldThrowFileNotFoundExceptionWhenFileNotFound() {

    Assertions.assertThrows(
        FileNotFoundException.class,
        () -> {
          receitaService.readCsv(nonExistingFile);
        });
  }

  @Test
  void getOutputPathShouldThrowRuntimeExceptionWhenOutputPathIsInvalid() {
    Assertions.assertThrows(
        RuntimeException.class,
        () -> {
          receitaService.getOutputPath();
        });
  }

  @Test
  void writeCsvShouldThrowRuntimeExceptionWhenWriterFailed() {
    Assertions.assertThrows(
        RuntimeException.class,
        () -> {
          receitaService.writeCsv(receitaSyncList);
        });
  }
}
