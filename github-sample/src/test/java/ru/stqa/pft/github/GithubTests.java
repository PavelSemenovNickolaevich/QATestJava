package ru.stqa.pft.github;

import com.google.common.collect.ImmutableMap;
import com.jcabi.github.*;
import org.testng.annotations.Test;

import java.io.IOException;

public class GithubTests {

    @Test
    public void testCommits () throws IOException {
        Github github = new RtGithub(" 4851c56e0bfddf6b05be264b137e89ec332c8c44 ");
        RepoCommits commits = github.repos().get(new Coordinates.Simple("PavelSemenovNickolaevich", "QATestJava"))
                .commits();
        for (RepoCommit commit : commits.iterate(new ImmutableMap.Builder<String, String>().build())) {
            System.out.println(new RepoCommit.Smart(commit).message());
        }
    }
}
