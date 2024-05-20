package com.splitwise.service;

import com.splitwise.entity.Contributers;
import com.splitwise.entity.Expenses;
import com.splitwise.entity.Group;
import com.splitwise.entity.Members;
import com.splitwise.exception.ResourceNotFoundException;
import com.splitwise.repository.ExpeneseRepository;
import com.splitwise.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    @Autowired
    private ExpeneseRepository expeneseRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    public void createExpense(Expenses expenses) {

        Optional<Group> group = groupRepository.findById(expenses.getGroupId());
        if (!group.isEmpty()) {
            List<Contributers> contributers = expenses.getContributers();
            Contributers contributers1 = new Contributers();
            List<Members> members = group.get().getMembers();
            boolean isPayerPresent = false;

            for (Members members1 : members) {
                System.out.println("ExpenseService PaidBy:" + expenses.getPaidBy());
                System.out.println("ExpenseService MemberId:" + members1.getMemberId());
                if (members1.getMemberId() == expenses.getPaidBy()) {
                    isPayerPresent = true;
                }
            }
            if (!isPayerPresent) {
                throw new ResourceNotFoundException("Payer", "MemberId", String.valueOf(expenses.getPaidBy()));
            }
            if (expenses.getSplitType() != null && expenses.getSplitType().equalsIgnoreCase("equally")) {
                double amount = expenses.getAmount() / contributers.size();
                for (int i = 0; i < contributers.size(); i++) {
                    contributers1 = contributers.get(i);
                    boolean isMemberpresent = false;
                    for (Members members1 : members) {
                        System.out.println("ExpenseService ContId:" + contributers1.getContId());
                        System.out.println("ExpenseService MemberId:" + members1.getMemberId());
                        if (contributers1.getContId() == members1.getMemberId()) {
                            isMemberpresent = true;
                        }
                    }
                    System.out.println("ExpenseService isMemberpresent:" + isMemberpresent);
                    if (!isMemberpresent) {
                        throw new ResourceNotFoundException("Members", "Id", String.valueOf(contributers1.getContId()));
                    }
                    System.out.println("contributers1" + contributers1.getContId());
                    contributers1.setAmount(amount);
                }
            }
            expenses.setExpId(sequenceGeneratorService.generateSequence(Expenses.SEQUENCE_NAME));
            expeneseRepository.save(expenses);
        } else {
            throw new ResourceNotFoundException("Group", "Id", String.valueOf(expenses.getGroupId()));
        }
    }
    public List<Expenses> getExpenseList() {
        List<Expenses> expenses = expeneseRepository.findAll();
        return expenses;
    }
}
