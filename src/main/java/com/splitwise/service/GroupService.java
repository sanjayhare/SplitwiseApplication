package com.splitwise.service;

import com.splitwise.entity.Group;
import com.splitwise.entity.Members;
import com.splitwise.exception.RecordAlreadyExistsException;
import com.splitwise.exception.ResourceNotFoundException;
import com.splitwise.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    public void createGroup(Group group) {

        Optional<Group> group1 = groupRepository.findByTitle(group.getTitle().trim());
        System.out.println("GroupService group::" + group.toString());
        System.out.println("GroupService group1::" + group1.toString());
        if (!group1.isEmpty()) {
            throw new RecordAlreadyExistsException("Group Name Already Exist");
        }
        List<Members> members = group.getMembers();
        Members members1 = new Members();
        for (int i = 0; i < members.size(); i++) {
            members1 = members.get(i);
            members1.setMemberId(i + 1L);
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
        if(group.isEmpty()){
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

    public boolean deleteMember(Long gId , Long mId ) {
        boolean isDeleted = false;
        Optional<Group> group = groupRepository.findById(gId);
        if(group.isEmpty()){
            throw new ResourceNotFoundException("Group", "GroupID", String.valueOf(gId));
        }
        List<Members> members = group.get().getMembers();
        for(Members members1 : members)
        {
            if(members1.getMemberId()== mId){
                members.remove(members1);
            }
        }
        group.get().setMembers(members);
        groupRepository.save(group.get());
        isDeleted = true;
        return isDeleted;
    }

    public boolean update(Long gId , Long mId ) {
        boolean isDeleted = false;
        Optional<Group> group = groupRepository.findById(gId);
        if(group.isEmpty()){
            throw new ResourceNotFoundException("Group", "GroupID", String.valueOf(gId));
        }
        List<Members> members = group.get().getMembers();
        for(Members members1 : members)
        {
            if(members1.getMemberId()== mId){
                members.remove(members1);
            }
        }
        group.get().setMembers(members);
        groupRepository.save(group.get());
        isDeleted = true;
        return isDeleted;
    }

}
