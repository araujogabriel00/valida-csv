package com.example.sincronizacaoreceita.factory;

import com.example.sincronizacaoreceita.entities.ReceitaSync;

import java.util.Arrays;
import java.util.List;

public class Factory {

  public static ReceitaSync createConta() {
    ReceitaSync conta = new ReceitaSync("0101", "12225-6", "100,00", "A");

    return conta;
  }

  public static List<ReceitaSync> createContaList() {
    ReceitaSync conta = new ReceitaSync("0101", "12225-6", "100,00", "A");

    return Arrays.asList(conta);
  }
}
