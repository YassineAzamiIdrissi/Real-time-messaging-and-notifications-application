package com.example.real_time.Group;

import org.springframework.stereotype.Service;

@Service
public class GroupMapper {
    public GroupRespDto groupToResp(Group group) {
        return GroupRespDto.builder().
                name(group.getName()).
                id(group.getId()).
                creator(group.getGrpCreator().fullName()).
                members(group.getGroups().size()).
                createdAt(group.getCreatedAt()).
                build();
    }
}
