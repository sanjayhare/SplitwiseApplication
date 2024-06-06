package com.splitwise.service;

import com.splitwise.dto.MembersDto;
import com.splitwise.entity.Contributers;
import com.splitwise.entity.Expenses;
import com.splitwise.entity.Group;
import com.splitwise.entity.Members;
import com.splitwise.repository.ExpeneseRepository;
import com.splitwise.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Vector;

@Service
public class MembersService {

    @Autowired
    private ExpeneseRepository expeneseRepository;
    @Autowired
    private GroupRepository groupRepository;


    public List<MembersDto> getMembersExpensesList() {
        List<MembersDto> membersList = new ArrayList<>();
        List<MembersDto> finalMembersList = new ArrayList<>();

        membersList = getMembersExpenses();
        MembersDto membersDto = new MembersDto();
        for (int i = 0; i < membersList.size(); i++) {
            membersDto = membersList.get(i);
            MembersDto membersDto1 = new MembersDto();
            for (int j = 0; j < membersList.size(); j++) {
                if (i != j) {
                    membersDto1 = membersList.get(j);
                    if(membersDto.getMemberName().equalsIgnoreCase(membersDto1.getMemberName()))
                    {
                        membersDto.setAmount(membersDto.getAmount().add(membersDto1.getAmount()));
                        membersList.remove(membersDto1);
                    }
                }
            }
            finalMembersList.add(membersDto);
        }
        return finalMembersList;
    }


    public List<MembersDto> getMembersExpenses() {
        List<MembersDto> membersDtoVector = new ArrayList<>();
        List<Group> groups = groupRepository.findAll();

        Group group = new Group();
        for (int i = 0; i < groups.size(); i++) {
            group = groups.get(i);

            List<Members> members = group.getMembers();

            List<Expenses> expenses = expeneseRepository.findByGroupId(group.getGroupId());
            Expenses expenses1 = new Expenses();
            for (int j = 0; j < expenses.size(); j++) {
                expenses1 = expenses.get(j);
                Contributers contributer = new Contributers();
                for (int k = 0; k < expenses1.getContributers().size(); k++) {
                    contributer = expenses1.getContributers().get(k);

                    Members member = new Members();

                    MembersDto membersDto = new MembersDto();
                    for (int l = 0; l < members.size(); l++) {
                        member = members.get(l);
                        if (member.getMemberId() == contributer.getContId()) {
                            membersDto.setMemberId(member.getMemberId());
                            membersDto.setMemberName(member.getName());
                            membersDto.setGroupId(group.getGroupId());
                            membersDto.setAmount(contributer.getAmount());
                        }
                    }
                    membersDtoVector.add(membersDto);
                }
            }
        }
        return membersDtoVector;
    }
}
