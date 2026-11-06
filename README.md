# Loja de Consoles · Atividade de POO em Java

Atividade da disciplina GCVA: partir de um código com problemas de design e corrigi-lo, passo a passo, aplicando os pilares da orientação a objetos.

| Parte | Conceito | O que foi feito |
| --- | --- | --- |
| 1 e 2 | Encapsulamento e construtores | `Console` com atributos privados e imutáveis, validados no construtor (nome e tipo obrigatórios, preço não negativo) |
| 3 | Interface e composição | Contrato `IConsole`; `Nintendo` e `Playstation` reaproveitam `DadosConsole` por composição |
| 4 | Herança | `PlaystationPortatil` estende `Playstation` |
| 5 | Polimorfismo e princípio aberto/fechado | `Xbox` entra na `Loja` sem alterar o código dela |

## Como executar

Requer JDK 17 ou superior.

```bash
javac Atividade_POO_Problema.java
java Atividade_POO_Problema
```
