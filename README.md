# Amharic-NLP-Tools-in-JAVA
## Amharic NLP Tools in Java

This repository contains implementations of various Natural Language Processing (NLP) tasks and tools specifically for the Amharic language using Java. The goal is to provide a comprehensive set of tools to facilitate NLP research and development for Amharic.

## Features

- **Tokenization**: Splitting text into words, sentences, or other meaningful units.
- **Sentence Segmentation**: Dividing text into individual sentences.
- **Sentence Boundary Detection**: Identifying the boundaries of sentences within a text.
- **Normalization**:
  - Character normalization
  - Abbreviation substitution
  - Strange character, word, and symbol removal
  - Removal of emojis
  - Removal of emoticons
  - Removal of punctuations
  - Conversion of emoticons to words
  - Conversion of emojis to words
- **StopWord Removal**: Removing common words that do not carry significant meaning.
- **Noun Phrase Chunking**: Identifying and grouping noun phrases.
- **Lemmatization**: Reducing words to their base or root form.
- **Stemming**: Reducing words to their root form by removing suffixes.
- **Named Entity Recognition (NER)**: Identifying and classifying named entities in text.
- **Part-of-Speech (POS) Tagging**: Assigning parts of speech to each word in a sentence.
- **Word-Sense Disambiguation**: Determining the correct meaning of a word based on context.
- **Co-reference Resolution**: Identifying when different expressions refer to the same entity.
- **Entity Linking**: Connecting entities mentioned in the text to their corresponding entries in a knowledge base.
- **Terminology Extraction**: Extracting domain-specific terms from text.
- **Discourse Parsing**: Analyzing the structure of discourse in text.
- **Sentiment Analysis**: Determining the sentiment expressed in a piece of text.
- **Text Classification**: Categorizing text into predefined categories.
- **Language Modeling**: Building models to predict the next word in a sequence.
- **Machine Translation**: Translating text from Amharic to other languages and vice versa

## Project Structure
```
amharic-nlp-tools-in-java/
│
├── README.md
├── src/
│   ├── main/
│   │   └── java/
│   │       └── nlp/
│   │           ├── Tokenizer.java
│   │           ├── SentenceSegmenter.java
│   │           ├── SentenceBoundaryDetector.java
│   │           ├── Normalizer.java
│   │           ├── StopWordRemover.java
│   │           ├── NounPhraseChunker.java
│   │           ├── Lemmatizer.java
│   │           ├── Stemmer.java
│   │           ├── NER.java
│   │           ├── POSTagger.java
│   │           ├── WordSenseDisambiguator.java
│   │           ├── CoReferenceResolver.java
│   │           ├── EntityLinker.java
│   │           ├── TerminologyExtractor.java
│   │           ├── DiscourseParser.java
│   │           ├── SentimentAnalyzer.java
│   │           ├── TextClassifier.java
│   │           ├── LanguageModel.java
│   │           └── Translator.java
│   └── test/
│       └── java/
│           └── nlp/
│               └── NLPToolsTest.java

```

- `src/main/java/nlp/`: Contains the main implementation of each NLP tool.
  - `Tokenizer.java`: Implementation of text tokenization.
  - `SentenceSegmenter.java`: Implementation of sentence segmentation.
  - `SentenceBoundaryDetector.java`: Implementation of sentence boundary detection.
  - `Normalizer.java`: Implementation of text normalization.
  - `StopWordRemover.java`: Implementation of stop word removal.
  - `NounPhraseChunker.java`: Implementation of noun phrase chunking.
  - `Lemmatizer.java`: Implementation of lemmatization.
  - `Stemmer.java`: Implementation of stemming.
  - `NER.java`: Implementation of named entity recognition.
  - `POSTagger.java`: Implementation of part-of-speech tagging.
  - `WordSenseDisambiguator.java`: Implementation of word-sense disambiguation.
  - `CoReferenceResolver.java`: Implementation of co-reference resolution.
  - `EntityLinker.java`: Implementation of entity linking.
  - `TerminologyExtractor.java`: Implementation of terminology extraction.
  - `DiscourseParser.java`: Implementation of discourse parsing.
  - `SentimentAnalyzer.java`: Implementation of sentiment analysis.
  - `TextClassifier.java`: Implementation of text classification.
  - `LanguageModel.java`: Implementation of language modeling.
  - `Translator.java`: Implementation of machine translation.
- `src/test/java/nlp/`: Contains unit tests for each tool to ensure they work correctly.
  - `NLPToolsTest.java`: Unit tests for the NLP tools.

## Getting Started

### Prerequisites

- **Java Development Kit (JDK)**: Make sure you have JDK installed on your system. You can download it from oracle.com.
- **Maven**: A build automation tool for Java projects. You can download it from maven.apache.org.

### Installation

Clone the repository to your local machine:

```bash
git clone https://github.com/yourusername/amharic-nlp-tools-in-java.git
cd amharic-nlp-tools-in-java
```
## Contributing
Contributions are welcome! Please fork the repository and create a pull request with your changes.

## License
This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments
Special thanks to the open-source community for their invaluable contributions and resources.

```
Bushra KM.
Debre Berhan University, Ethiopia
2019
--------

