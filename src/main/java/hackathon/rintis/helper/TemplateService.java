package hackathon.rintis.helper;

import hackathon.rintis.model.entity.MessageTemplate;
import hackathon.rintis.repository.MessageTemplateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TemplateService {

    @Autowired
    private MessageTemplateRepo templateRepo;

    public String generateMessage(Integer id, Map<String, String> params) {
        MessageTemplate template = templateRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Template not found"));

        String result = template.getText_prompt();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            String placeholder = "{" + entry.getKey() + "}";
            result = result.replace(placeholder, entry.getValue());
        }

        return result;
    }
}
