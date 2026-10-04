package springbootai.budgeting;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ai.chat.client.ChatClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnabledIfEnvironmentVariable(named = "OPENAI_API_KEY", matches = ".+")
public class OpenAiSpeechModelIT {

    @Autowired
    private ChatClient chatClient;

    @ParameterizedTest
    @CsvSource({
            "Gastei oitenta reais no mercado",
            "Paguei quarenta reais na padaria",
            "Cento e vinte reais na farmácia",
            "Noventa reais no restaurante",
            "Duzentos reais na gasolina"
    })
    void should_generateSpeech_when_textIsProvided(String text) {
        // TODO: Implementar teste de geração de voz
        assertThat(chatClient).isNotNull();
    }

    @Test
    void should_convertTextToSpeech_successfully() {
        // TODO: Implementar teste de conversão de texto para fala
        assertThat(chatClient).isNotNull();
    }
}
