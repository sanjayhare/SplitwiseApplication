package com.splitwise.service;

import com.splitwise.entity.Contributers;
import com.splitwise.entity.Expenses;
import com.splitwise.entity.Group;
import com.splitwise.entity.Members;
import com.splitwise.exception.ResourceNotFoundException;
import com.splitwise.exception.SplitWiseMessegeException;
import com.splitwise.repository.ExpeneseRepository;
import com.splitwise.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.TextIndexDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
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

    @Autowired
    private MongoTemplate mongoTemplate;

    public void createExpense(Expenses expenses) {

        System.out.println("ExpenseService in createExpense");
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
                    System.out.println("ExpenseService contributers1" + contributers1.getContId());
                    contributers1.setAmount(BigDecimal.valueOf(amount));
                }
            } else if (expenses.getSplitType() != null && expenses.getSplitType().equalsIgnoreCase("propotionally")) {
                System.out.println("ExpenseService in propotionally loop");
                double totalProportion = 0.00;
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
                    System.out.println("ExpenseService getProportion:" + contributers1.getProportion());
                    totalProportion = totalProportion + contributers1.getProportion();

                }
                System.out.println("ExpenseService totalProportion:" + totalProportion);
                double amount = expenses.getAmount();
                double splitAmount = amount / totalProportion;
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
                    double amountAssigned = splitAmount * contributers1.getProportion();
                    BigDecimal assignedAmount = BigDecimal.valueOf(amountAssigned);
                    System.out.println("ExpenseService amountAssigned:" + amountAssigned);
                    contributers1.setAmount(assignedAmount.setScale(2, RoundingMode.HALF_UP));
                }

            } else if (expenses.getSplitType() != null && expenses.getSplitType().equalsIgnoreCase("byAmount")) {

                System.out.println("ExpenseService in byAmount loop:");
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
                }
            } else {
                System.out.println("Invalid Split type");
                throw new SplitWiseMessegeException("Invalid Split type");
            }
            expenses.setExpId(sequenceGeneratorService.generateSequence(Expenses.SEQUENCE_NAME));
            expenses.setDateTime(LocalDateTime.now());
            expeneseRepository.save(expenses);
        } else {
            throw new ResourceNotFoundException("Group", "GroupID", String.valueOf(expenses.getGroupId()));

        }
    }

    public List<Expenses> getExpenseList() {
        List<Expenses> expenses = expeneseRepository.findAll();
        return expenses;
    }

    public Optional<Expenses> getExpense(String id) {

        Optional<Expenses> expenses = expeneseRepository.findById(Long.valueOf(id));
        return expenses;
    }

    public List<Expenses> getExpensesBySorting(int pageNum, String sortField, String sortDir) {
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
                sortDir.equals("asc") ? Sort.by(sortField).ascending()
                        : Sort.by(sortField).descending());
        Page<Expenses> expensesPage = expeneseRepository.findAll(pageable);
        if (expensesPage.isEmpty() || expensesPage == null) {
            throw new SplitWiseMessegeException("No Records Found !!");
        }
        return expensesPage.getContent();
    }

    public List<Expenses> fullTextSearch(String searchPhrase) {

        System.out.println("ExpenseService in fullTextSearch:");
        mongoTemplate.indexOps(Expenses.class)
                .ensureIndex(new TextIndexDefinition.TextIndexDefinitionBuilder().onFields("title", "attributes.attribute_value").build());
        TextCriteria textCriteria = TextCriteria
                .forDefaultLanguage()
                .caseSensitive(false)
                .matchingPhrase(searchPhrase);
        Query query = TextQuery.queryText(textCriteria)
                .sortByScore();
        System.out.println("ExpenseService query:" + query.toString());
        return mongoTemplate.find(query, Expenses.class);
    }

    //update expense
    public boolean updateExpense(Expenses expenses) {

        boolean isUpdated = false;
        System.out.println("ExpenseService in createExpense");
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
                    System.out.println("ExpenseService contributers1" + contributers1.getContId());
                    contributers1.setAmount(BigDecimal.valueOf(amount));
                }
            } else if (expenses.getSplitType() != null && expenses.getSplitType().equalsIgnoreCase("propotionally")) {
                System.out.println("ExpenseService in propotionally loop");
                double totalProportion = 0.00;
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
                    System.out.println("ExpenseService getProportion:" + contributers1.getProportion());
                    totalProportion = totalProportion + contributers1.getProportion();

                }
                System.out.println("ExpenseService totalProportion:" + totalProportion);
                double amount = expenses.getAmount();
                double splitAmount = amount / totalProportion;
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
                    double amountAssigned = splitAmount * contributers1.getProportion();
                    BigDecimal assignedAmount = BigDecimal.valueOf(amountAssigned);
                    System.out.println("ExpenseService amountAssigned:" + amountAssigned);
                    contributers1.setAmount(assignedAmount.setScale(2, RoundingMode.HALF_UP));
                }

            } else if (expenses.getSplitType() != null && expenses.getSplitType().equalsIgnoreCase("byAmount")) {

                System.out.println("ExpenseService in byAmount loop:");
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
                }
            } else {
                System.out.println("Invalid Split type");
                throw new SplitWiseMessegeException("Invalid Split type");
            }
            expenses.setDateTime(LocalDateTime.now());
            expeneseRepository.save(expenses);
        } else {
            throw new ResourceNotFoundException("Group", "GroupID", String.valueOf(expenses.getGroupId()));

        }
        return isUpdated;
    }

    public boolean deleteExpense(String expId) {
        boolean isDeleted = false;
        expeneseRepository.deleteById(Long.valueOf(expId));
        isDeleted = true;
        return isDeleted;
    }
}
