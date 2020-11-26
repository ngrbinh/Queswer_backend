package com.bdt.queswer.service;

import com.bdt.queswer.dto.UserPageDto;
import com.bdt.queswer.dto.request.ChangeProfileRequest;
import com.bdt.queswer.dto.request.SignUpRequest;
import com.bdt.queswer.dto.UserDto;
import com.bdt.queswer.dto.UserProfileDto;
import com.bdt.queswer.dto.request.VoteRequest;
import com.bdt.queswer.exception.CustomException;
import com.bdt.queswer.model.*;
import com.bdt.queswer.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    NotificationService notificationService;

    public UserPageDto getListUser(int limit, int pageNumber, String sortCrit) throws CustomException {
        UserPageDto dto = new UserPageDto();
        List<UserDto> dtos = new ArrayList<>();
        dto.setUsers(dtos);
        Sort sort;
        String order = sortCrit.substring(0, 1);
        switch (sortCrit.substring(1)) {
            case "name":
                sort = Sort.by("displayName");
                break;
            case "follow":
                sort = Sort.by("followCount");
                break;
            case "point":
                sort = Sort.by("point");
                break;
            case "date":
                sort = Sort.by("birthDate");
                break;
            case "answer":
                sort = Sort.by("answerCount");
                break;
            case "question":
                sort = Sort.by("questionCount");
                break;
            default:
                throw new CustomException("Tham số sort không hợp lệ");
        }
        switch (order) {
            case "+":
                sort = sort.ascending();
                break;
            case "-":
                sort = sort.descending();
                break;
            default:
                throw new CustomException("Tham số sort không hợp lệ");
        }
        Pageable pageable = PageRequest.of(pageNumber - 1, limit, sort.and(Sort.by("id").ascending()));
        Page<User> userPage = userRepository.findAll(pageable);
        userPage.forEach(item -> dto.getUsers().add(modelMapper.map(item, UserDto.class)));
        dto.setTotalPage(userPage.getTotalPages());
        return dto;
    }

    public UserDto createNewUser(SignUpRequest signUpRequest, String roleName) throws CustomException {
        User user = modelMapper.map(signUpRequest, User.class);
        Account account = modelMapper.map(signUpRequest, Account.class);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        if (userRepository.existsByDisplayName(user.getDisplayName())) {
            throw new CustomException("Tên hiển thị đã được sử dụng");
        }
        if (accountRepository.existsByEmail(account.getEmail())) {
            throw new CustomException("Email đã được sử dụng");
        }
        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            throw new CustomException("Role " + roleName + " doesn't exist");
        } else account.setRole(role);
        user.setAccount(account);
        user.setPoint(50);
        userRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }

    public void editUser(ChangeProfileRequest req, long id) throws CustomException {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUserName = authentication.getName();
            Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) authentication.getAuthorities();
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ADMIN");
            User user = optional.get();
            /*System.out.println(currentUserName);
            System.out.println(user.getAccount().getEmail());*/
            if (!currentUserName.equals(user.getAccount().getEmail()) && !authorities.contains(authority)) {
                throw new CustomException("Truy cập không hợp lệ", HttpStatus.FORBIDDEN);
            }
            modelMapper.map(req, user);
            if (req.getAddressId() != null) {
                Optional<Address> optionalAddress = addressRepository.findById(req.getAddressId());
                if (!optionalAddress.isPresent()) {
                    throw new CustomException("AddressId không hợp lệ");
                }
                user.setAddress(optionalAddress.get());
            }
            user.setId(id);
            try {
                userRepository.save(user);
            } catch (Exception e) {
                throw new CustomException("Dữ liệu không hợp lệ");
            }
        } else {
            throw new CustomException("Đối tượng không tồn tại");
        }
    }

    public UserProfileDto getUserDetail(long id) throws Exception {
        //boolean isAdmin = false;
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            /*Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUserName = authentication.getName();*/
            /*Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) authentication.getAuthorities();
            authorities.forEach(item -> {
                System.out.println(item);
            });
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("[{authority=ADMIN}]");
            System.out.println(authority);
            if (authorities.contains(authority)) {
                System.out.println("Admin is here");
            }
            if (!currentUserName.equals(dto.getAccount().getEmail()) && !authorities.contains(authority)) {
                throw new CustomException("Truy cập không hợp lệ", HttpStatus.FORBIDDEN);
            }*/
            return modelMapper.map(optional.get(), UserProfileDto.class);
        } else {
            throw new CustomException("Đối tượng không tồn tại");
        }
    }

    public void deleteUser(Long id) throws CustomException {
        if (!userRepository.existsById(id)) {
            throw new CustomException("Đối tượng không tồn tại");
        }
        userRepository.deleteById(id);
    }

    public UserProfileDto getOwnDetails() throws CustomException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<User> optional = userRepository.findByAccountEmail(email);
        if (optional.isPresent()) {
            User user = optional.get();
            return modelMapper.map(user, UserProfileDto.class);
        } else {
            throw new CustomException("Đối tượng không tồn tại");
        }
    }

    public void follow(long id) throws CustomException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User curUser = userRepository.findByAccountEmail(email).get();
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            User user = optional.get();
            if (curUser.getFollowingUsers().contains(user)) {
                throw new CustomException("Đã follow");
            } else {
                curUser.getFollowingUsers().add(user);
                userRepository.save(curUser);
                user.setFollowCount(user.getFollowCount() + 1);
                user.setPoint(user.getPoint()+50);
                userRepository.save(user);
            }
        } else {
            throw new CustomException("Đối tượng không tồn tại");
        }
    }

    public int vote(VoteRequest request) throws CustomException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User curUser = userRepository.findByAccountEmail(email).get();
        Optional<Vote> optionalVote = voteRepository.findByPostIdAndUserId(request.getPostId(), curUser.getId());
        Optional<Post> optionalPost = postRepository.findById(request.getPostId());
        if (!optionalPost.isPresent()) {
            throw new CustomException("Post id không hợp lệ");
        }
        Post post = optionalPost.get();
        int value;
        if (optionalVote.isPresent()) {
            Vote vote = optionalVote.get();
            if (request.isVoteType() != vote.isVoteType()) {
                vote.setVoteType(request.isVoteType());
                voteRepository.save(vote);
                value = 2;
            } else {
                value = 0;
            }
        } else {
            Vote vote = new Vote();
            vote.setVoteType(request.isVoteType());
            vote.setUser(curUser);
            vote.setPost(post);
            voteRepository.save(vote);
            value = 1;
        }
        if (!request.isVoteType()) {
            value = value * (-1);
        }
        post.setVoteCount(post.getVoteCount() + value);
        postRepository.save(post);
        if (value!=0) {
            notificationService.addNotification(
                    curUser.getDisplayName() + " đã bình chọn cho bài đăng của bạn",
                    NotiType.VOTE, post.getId(), post.getOwnerId());
        }
        if (post.getPostType().getId() == 1) {
            userRepository.updatePointByUserId(post.getOwnerId(), value*30);
        }
        if (post.getPostType().getId() == 2) {
            userRepository.updatePointByUserId(post.getOwnerId(), value*60);
        }
        return value;
    }

    public void unFollow(long id) throws CustomException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User curUser = userRepository.findByAccountEmail(email).get();
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            User user = optional.get();
            if (!curUser.getFollowingUsers().contains(user)) {
                throw new CustomException("Chưa follow");
            } else {
                curUser.getFollowingUsers().remove(user);
                userRepository.save(curUser);
                user.setFollowCount(user.getFollowCount() - 1);
                userRepository.save(user);
            }
        } else {
            throw new CustomException("Đối tượng không tồn tại");
        }
    }
}
