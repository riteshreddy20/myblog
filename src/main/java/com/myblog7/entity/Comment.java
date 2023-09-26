package com.myblog7.entity;


import lombok.Data;


import javax.persistence.*;


@Data
@Entity
@Table(name= "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @Column(nullable=false)
    private String name;
    @Column(nullable=false)
    private String email;
    @Column(nullable=false)
    private String body;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="post_id",nullable = false)
    private Post post;


}
