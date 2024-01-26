package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Feedback;
import com.swp.online_quizz.Repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService implements IFeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepository;



    @Override
    public void deleteFeedbacksByQuizId(Integer attemptId) {
        List<Feedback> feedbacks = feedbackRepository.findByattemptId(attemptId);
        for (Feedback feedback : feedbacks) {
            feedbackRepository.deleteById(feedback.getFeedbackId());
        }

    }
}
