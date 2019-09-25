package lift.majiang.community.dto;

public class GitHubUser {
    private String name ;
    private Long id;
    private String bio;

    public String getName() {
        return name;
    }
    //alt insert 快捷键，可以快速生成set和get方法。
    @Override
    public String toString() {
        return "GitHubUser{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", bio='" + bio + '\'' +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
