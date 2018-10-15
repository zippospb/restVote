package ru.zippospb.restvote.web.vote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.zippospb.restvote.AuthorizedUser;
import ru.zippospb.restvote.model.Vote;
import ru.zippospb.restvote.service.RestaurantService;
import ru.zippospb.restvote.service.VoteService;
import ru.zippospb.restvote.util.exception.TooLateToVoteException;
import ru.zippospb.restvote.web.security.SecurityUtil;

import java.time.LocalTime;

import static ru.zippospb.restvote.util.ValidationUtil.checkNotFound;
import static ru.zippospb.restvote.web.security.SecurityUtil.authUserId;
import static ru.zippospb.restvote.web.security.SecurityUtil.saveGet;

@RestController
@RequestMapping(ProfileVoteRestController.REST_URL)
public class ProfileVoteRestController {
    final static String REST_URL = "/rest/profile/";

    private final LocalTime END_TIME_OF_VOTE = LocalTime.of(11, 0, 0);

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final VoteService voteService;

    private final RestaurantService restService;

    @Autowired
    public ProfileVoteRestController(VoteService voteService, RestaurantService restService) {
        this.voteService = voteService;
        this.restService = restService;
    }

    @GetMapping("restaurants/{restId}/votes")
    @Transactional
    public Vote vote(@PathVariable("restId") int restId){
        AuthorizedUser user = saveGet();
        assert user != null;

        Vote vote = voteService.getByUser(user.getId());

        if(vote == null){
            log.info("create vote by user {} for restaurant {}", user, restId);
            vote = new Vote(SecurityUtil.get().getUser(), restService.get(restId));
        } else if (LocalTime.now().isBefore(END_TIME_OF_VOTE)){
            log.info("update vote by user {} for restaurant {}", user, restId);
            vote.setRestaurant(restService.get(restId));
        } else {
            throw new TooLateToVoteException("you can`t to re-vote after " + END_TIME_OF_VOTE.toString());
        }

        return voteService.save(vote);
    }

    @GetMapping("/vote")
    public Vote get(){
        int userId = authUserId();
        return checkNotFound(voteService.getByUser(userId), "Not found vote for user with id=" + userId);
    }
}
