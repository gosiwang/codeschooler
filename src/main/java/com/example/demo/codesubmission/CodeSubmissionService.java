package com.example.demo.codesubmission;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CodeSubmissionService {

    private final CodeSubmissionRepository repository;



    // 제출 코드 저장
    public CodeSubmission saveSubmission(CodeSubmission submission) {
        return repository.save(submission);
    }

    // 특정 사용자(userId)의 제출 내역 조회
    public List<CodeSubmission> getSubmissionsByUserId(Long userId) {
        return repository.findByUserId(userId);
    }
}
// 제발 정신 좀 차리자 제발 정신 좀 차리자 정신좀 차리자