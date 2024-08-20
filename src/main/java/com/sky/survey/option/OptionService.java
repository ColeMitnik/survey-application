package com.sky.survey.option;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OptionService {

    @Autowired
    private OptionRepository optionRepository;

    public List<Option> getAllOptions() {
        return optionRepository.findAll();
    }

    public Option getOptionById(Long id) {
        return optionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Option not found with id: " + id));
    }

    public List<Option> getOptionsByQuestionId(Long questionId) {
        return optionRepository.findByQuestionId(questionId);
    }

    public Option createOption(Option option) {
        return optionRepository.save(option);
    }

    public Option updateOption(Long id, Option optionDetails) {
        Option option = getOptionById(id);
        option.setOptionText(optionDetails.getOptionText());
        option.setQuestion(optionDetails.getQuestion());
        return optionRepository.save(option);
    }

    public void deleteOption(Long id) {
        optionRepository.deleteById(id);
    }
}