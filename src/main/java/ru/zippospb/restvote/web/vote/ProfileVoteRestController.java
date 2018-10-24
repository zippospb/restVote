package ru.zippospb.restvote.web.vote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.zippospb.restvote.model.Vote;
import ru.zippospb.restvote.service.VoteService;
import ru.zippospb.restvote.web.security.SecurityUtil;

import java.time.LocalDate;

import static ru.zippospb.restvote.web.security.SecurityUtil.authUserId;

@RestController
@RequestMapping(ProfileVoteRestController.REST_URL)
public class ProfileVoteRestController {
    final static String REST_URL = "/rest/";

    private final VoteService voteService;

    @Autowired
    public ProfileVoteRestController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping("restaurants/{restId}/votes")
    public Vote vote(@PathVariable("restId") int restId){
        return voteService.vote(SecurityUtil.get().getUser(), restId);
    }

    @GetMapping("profile/vote")
    public Vote get(){
        Vote vote = voteService.getByUserIdAndDate(authUserId(), LocalDate.now());
        return vote == null ? getEmptyVoteByDate(LocalDate.now()) : vote;
    }

    @GetMapping("profile/vote/by")
    public Vote getByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        Vote vote = voteService.getByUserIdAndDate(authUserId(), date);
        return vote == null ? getEmptyVoteByDate(date) : vote;
    }

    private Vote getEmptyVoteByDate(LocalDate date) {
        return new Vote(SecurityUtil.get().getUser(), date);
    }
}
