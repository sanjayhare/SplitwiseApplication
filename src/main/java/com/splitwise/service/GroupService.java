package com.splitwise.service;

import com.splitwise.entity.Contributers;
import com.splitwise.entity.Expenses;
import com.splitwise.entity.Group;
import com.splitwise.entity.Members;
import com.splitwise.exception.RecordAlreadyExistsException;
import com.splitwise.exception.ResourceNotFoundException;
import com.splitwise.exception.SplitWiseMessegeException;
import com.splitwise.repository.ExpeneseRepository;
import com.splitwise.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ExpeneseRepository expeneseRepository;
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    public void createGroup(Group group ,Principal principal) {

        Optional<Group> group1 = groupRepository.findByTitle(group.getTitle().trim());
        System.out.println("GroupService group::" + group.toString());
        System.out.println("GroupService group1::" + group1.toString());
        if (!group1.isEmpty()) {
            throw new RecordAlreadyExistsException("Group Name Already Exist");
        }
        List<Members> members = group.getMembers();
        boolean isUserPresnet = false;
        Members members1 = new Members();
        System.out.println("GroupService principal=" +principal.getName());

        for (int i = 0; i < members.size(); i++) {
            members1 = members.get(i);
            if(members1.getName().equalsIgnoreCase(principal.getName()))
            {
                isUserPresnet= true;
            }
            members1.setMemberId(i + 1L);
        }
        if(!isUserPresnet)
        {
            throw  new SplitWiseMessegeException("You cannot remove yourself from group");
        }
        group.setGroupId(sequenceGeneratorService.generateSequence(Group.SEQUENCE_NAME));
        group.setTitle(group.getTitle().trim());
        groupRepository.save(group);
    }

    public List<Group> getGroupList() {
        List<Group> groups = groupRepository.findAll();
        return groups;
    }

    public Optional<Group> getGroup(Long id) {
        Optional<Group> group = groupRepository.findById(id);
        if (group.isEmpty()) {
            throw new ResourceNotFoundException("Group", "GroupID", String.valueOf(id));
        }
        return group;
    }

    public boolean deleteGroup(Long id) {
        boolean isDeleted = false;
        groupRepository.deleteById(id);
        isDeleted = true;
        return isDeleted;
    }

    public boolean deleteMember(Long gId, Long mId) {
        boolean isDeleted = false;
        Optional<Group> group = groupRepository.findById(gId);
        if (group.isEmpty()) {
            throw new ResourceNotFoundException("Group", "GroupID", String.valueOf(gId));
        }
        List<Expenses> expenses = expeneseRepository.findByGroupId(gId);
        Expenses expenses1 = new Expenses();
        for (int i = 0; i < expenses.size(); i++) {
            expenses1 = expenses.get(i);
            List<Contributers> contributers = expenses1.getContributers();

            Contributers contributers1 = new Contributers();
            for (int j = 0; j < contributers.size(); j++) {

                contributers1 = contributers.get(j);
                System.out.println("Invalid Split type contributers1" + contributers1.getContId() + ":" + contributers1.getAmount());
                if (contributers1.getContId() == mId) {

                    if (Double.parseDouble(String.valueOf(contributers1.getAmount())) > 0.00) {
                        throw new SplitWiseMessegeException("Expenses are pending for this Contributer");
                    }
                    contributers.remove(contributers1);
                }
            }
            expenses1.setContributers(contributers);
            expeneseRepository.save(expenses1);
        }
        List<Members> members = group.get().getMembers();
        Members members1 = new Members();

        for (int i = 0; i < members.size(); i++) {
            members1 = members.get(i);
            if (members1.getMemberId() == mId) {
                members.remove(members1);
            }
        }
        group.get().setMembers(members);
        groupRepository.save(group.get());
        isDeleted = true;
        return isDeleted;
    }

    public boolean updateGroup(Group group) {
        boolean isUpdated = false;
        Optional<Group> group1 = groupRepository.findById(group.getGroupId());
        if (group1.isEmpty()) {
            throw new ResourceNotFoundException("Group", "GroupID", String.valueOf(group.getGroupId()));
        }
        List<Members> members = group.getMembers();
        Members members1 = new Members();
        for (int i = 0; i < members.size(); i++) {
            members1 = members.get(i);
            members1.setMemberId(i + 1L);
        }
        groupRepository.save(group);
        isUpdated = true;
        return isUpdated;
    }

}
