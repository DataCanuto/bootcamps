package springbootai.budgeting;

import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ai.chat.client.ChatClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnabledIfEnvironmentVariable(named = "OPENAI_API_KEY", matches = ".+")
public class OpenAiTranscriptionModelIT {

    @Autowired
    private ChatClient chatClient;

    @ParameterizedTest
    @CsvSource({
            "src/test/resources/audio/recording-1.m4a, 80 reais",
            "src/test/resources/audio/recording-2.m4a, 40 reais",
            "src/test/resources/audio/recording-3.m4a, 120 reais",
            "src/test/resources/audio/recording-4.m4a, 90 reais",
            "src/test/resources/audio/recording-5.m4a, 200 reais",
            "src/test/resources/audio/recording-6.m4a, 60 reais"
    })
    void should_transcribeAudio_successfully(String audioFile, String expectedKeyword) {
        // TODO: Implementar teste de transcrição
        assertThat(chatClient).isNotNull();
    }
}
