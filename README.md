# Mobiltec - Desafio de controle de combustível

## Descrição do desafio
* A ideia é que você faça um programa de linha de comando (sem Interface de Usuário) compatível com Windows, acompanhado do seu código fonte.
* O programa deve se chamar `LogCombustivel`.
* Você poderá escolher as seguintes linguagens de programação para realizar esta tarefa: C#, Java ou C/C++.

### Tarefas
* Ler um arquivo chamado `LogCombustivel.csv` que contém as seguintes colunas:
    * MARCA
    * MODELO
    * DATA
    * QUILOMETRAGEM
    * COMBUSTIVEL
    * PRECO

* Como saída apresentar um arquivo chamado `RelatorioConsumo.csv` contendo para cada veículo as seguintes colunas.
    * MARCA
    * MODELO
    * KM `(A quilometragem total no período)`
    * R$ `(O custo total com combustível no período)`
    * LITROS `(O consumo total de combustível no período)`
    * DATAINI `(Data inicial do período)`
    * DIAS `(Total de dias do período)`
    * MEDIAKM/L `(O consumo médio de combustível)`
    * MELHORKM/L `(O melhor registro de consumo (Km/L))`
    * PIORKM/L `(O pior registro de consumo (Km/L))`
    * R$/Km `(O custo médio por quilômetro rodado)`

### Observações
* Os abastecimentos são todos totais (completam o tanque do veículo).
* A quilometragem é o valor registrado no odômetro do veículo no momento do
abastecimento.
* O arquivo de entrada não está necessariamente ordenado
* Tanto o arquivo de entrada quanto o de saída deverão estar na mesma pasta do executável.

## Baixando o código fonte e executando
Faça o download ou clone o repositório da aplicação que está no Git através do seguinte [link](https://github.com/advecchia/mobiltec-challenge.git "Baixar repositório").
> $ git clone https://github.com/advecchia/mobiltec-challenge.git  
> $ cd mobiltec-challenge  
> $ javac src/logfuel/*.java  
> $ java -cp ./src logfuel.LogCombustivel  

