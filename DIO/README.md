# 🎓 Bootcamps DIO — Pedro Canuto

> Repositório central dos projetos desenvolvidos durante os bootcamps e trilhas de formação da **Digital Innovation One (DIO)** — cobrindo Java, Python, Data Science, IA Generativa e engenharia de software com IA.

[![DIO](https://img.shields.io/badge/DIO-Digital%20Innovation%20One-ff2d55)](https://www.dio.me/)
[![Java](https://img.shields.io/badge/Java-17+-orange?logo=openjdk)](https://openjdk.org/)
[![Python](https://img.shields.io/badge/Python-3.x-blue?logo=python)](https://www.python.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0-6DB33F?logo=springboot)](https://spring.io/projects/spring-boot)
[![OpenAI](https://img.shields.io/badge/OpenAI-API-412991?logo=openai)](https://openai.com/)

---

## 📌 Visão Geral

| Projeto | Trilha / Bootcamp | Stack Principal | Tema |
|---------|------------------|-----------------|------|
| [🎮 Sudoku](#-sudoku) | Java Developer | Java 17, Swing | POO + Padrão Observer |
| [📊 Data Science Bootcamp](#-data-science-bootcamp) | Python / Data Science | Python, Pandas, OpenAI | ETL + IA Generativa |
| [🎙️ GenAI Bootcamp](#%EF%B8%8F-genai-bootcamp) | Generative AI | Whisper, ChatGPT, gTTS | Speech-to-Text / Text-to-Speech |
| [🧩 My First Copilot](#-my-first-copilot) | AI Developer | Prompt Engineering | Modos do Copiloto |
| [🤖 Bia do Futuro](#-bia-do-futuro--edu) | IA Lab | Streamlit, Ollama | Agente Educador Financeiro |
| [💰 Spring Boot AI Budgeting](#-spring-boot-ai-budgeting) | Spring Boot Expert | Spring AI, Java | API de Orçamento com Voz |

---

## 🎮 Sudoku

**Trilha:** Java Developer | **Repositório:** [`Sudoku/`](./Sudoku/)

Jogo de Sudoku desenvolvido em Java com duas interfaces: **modo terminal** e **modo gráfico (Java Swing)**.

**Destaques técnicos:**
- Modelagem de domínio com POO (`Board`, `Space`, `GameStatusEnum`)
- Padrão de projeto **Observer** para comunicação entre componentes
- Interface gráfica com `JFrame`, `JPanel`, campos de entrada customizados e diálogos
- Configuração externa do tabuleiro via argumentos de linha de comando

**Stack:** `Java 17` · `Java Swing` · `Observer Pattern` · `OOP`

---

## 📊 Data Science Bootcamp

**Trilha:** Python / Data Science | **Repositório:** [`DIO-DataScienceBootCamp/`](./DIO-DataScienceBootCamp/)

Pipeline ETL que integra **pandas** e a **API da OpenAI** para extrair, transformar e enriquecer dados com insights gerados por IA Generativa.

**Destaques técnicos:**
- Pipeline ETL completo em Python
- Consumo da OpenAI API para geração de narrativas sobre dados
- Manipulação de DataFrames e exportação de resultados
- Materiais sobre AWS e dashboards de análise

**Stack:** `Python 3.13+` · `Pandas` · `OpenAI API` · `ETL`

---

## 🎙️ GenAI Bootcamp

**Trilha:** Generative AI | **Repositório:** [`DIO-GenAiBootcamp/`](./DIO-GenAiBootcamp/)

Pipeline de voz ponta a ponta: grava áudio no navegador, transcreve com **Whisper**, processa com **ChatGPT** e responde em voz com **gTTS** — tudo no Google Colab.

**Destaques técnicos:**
- Gravação de áudio via JavaScript no Google Colab
- Reconhecimento de fala com `whisper` (modelo `small`)
- Geração de resposta com `gpt-3.5-turbo`
- Síntese de voz em português com `gTTS`

**Stack:** `Python` · `Whisper` · `ChatGPT API` · `gTTS` · `Google Colab`

---

## 🧩 My First Copilot

**Trilha:** AI Developer Tools | **Repositório:** [`my-first-copilot/`](./my-first-copilot/)

Documentação e prompts dos **5 modos de interação do Copiloto** com IA — guia prático de prompt engineering para desenvolvimento de software assistido.

**Modos documentados:**
| Modo | Objetivo |
|------|----------|
| **Ask** | Entender código sem modificar |
| **Edit** | Alterar trechos específicos |
| **Plan** | Planejar antes de executar |
| **Agent** | Delegar tarefas autônomas |
| **Study** | Aprendizado ativo e guiado |

**Stack:** `Prompt Engineering` · `GitHub Copilot` · `Markdown`

---

## 🤖 Bia do Futuro — Edu

**Trilha:** IA Lab / Agentes | **Repositório:** [`dio-lab-bia-do-futuro/`](./dio-lab-bia-do-futuro/)

**Edu** é um agente educador financeiro que ensina conceitos de finanças pessoais usando os próprios dados do cliente como exemplos — 100% local com Ollama, sem enviar dados para APIs externas.

**Destaques técnicos:**
- Interface com **Streamlit**
- LLM rodando localmente com **Ollama** (`gpt-oss`)
- Dados mockados em JSON/CSV para personalização das respostas
- Estratégias documentadas de anti-alucinação e avaliação de qualidade

**Stack:** `Python` · `Streamlit` · `Ollama` · `LLM Local` · `Agentes de IA`

---

## 💰 Spring Boot AI Budgeting

**Trilha:** Spring Boot Expert (Projeto de Certificação) | **Repositório:** [`SpringBootAI-Budgetting-ProjectCertification/`](./SpringBootAI-Budgetting-ProjectCertification/)

API de orçamento pessoal com **Spring AI**: processa comandos de voz, transcreve com Whisper, executa ações financeiras via tool calling e retorna resposta em áudio.

**Destaques técnicos:**
- Arquitetura **DDD em camadas** (`domain` / `application` / `infrastructure`)
- **Tool Calling** com `@Tool` para expor casos de uso ao modelo
- Speech-to-text (Whisper) e Text-to-speech (TTS) integrados ao `ChatClient`
- Identificadores fortemente tipados (`TransactionId`)
- Docker Compose para infraestrutura local (MySQL)

**Stack:** `Java` · `Spring Boot 4` · `Spring AI` · `OpenAI` · `MySQL` · `Docker` · `DDD`

---

## 🛠️ Tecnologias Consolidadas

| Categoria | Ferramentas |
|-----------|-------------|
| **Linguagens** | Java 17+, Python 3.x |
| **Frameworks** | Spring Boot 4, Spring AI, Streamlit |
| **IA / LLM** | OpenAI API, Whisper, GPT-3.5/4, gTTS, Ollama |
| **Dados** | Pandas, Spring Data JPA, MySQL, H2 |
| **DevOps** | Docker, Docker Compose, Git, GitHub |
| **Ferramentas** | Google Colab, Jupyter Notebook, VS Code |
| **Padrões** | DDD, Observer, Repository, ETL, Tool Calling |

---

## 👤 Autor

**Pedro Canuto**
[data.canuto@gmail.com](mailto:data.canuto@gmail.com) · [GitHub: @DataCanuto](https://github.com/DataCanuto)

---

> 💡 *Cada projeto neste repositório representa uma entrega prática de bootcamp — com foco em boas práticas, arquitetura limpa e aplicação real de IA.*
