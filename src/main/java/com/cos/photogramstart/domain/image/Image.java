package com.cos.photogramstart.domain.image;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

import org.hibernate.annotations.ManyToAny;

import com.cos.photogramstart.domain.likes.Likes;
import com.cos.photogramstart.domain.subscribe.Subscribe;
import com.cos.photogramstart.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Image {
	// 연관관계 : 한명의 유저는 여러개의 사진을 등록 가능하지만, 하나의 이미지를 여러명이 등록은 불가하기 때문에
	// N : 1의 관계가 됨. ManyToOne
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String caption; // 사진에 관한 설명
	private String postImageUrl; // 사진을 전송 받아서 그 사진을 서버의 특정 폴더에 저장 - DB에 그 저장된 경로를 INSERT

	@JsonIgnoreProperties({ "images" })
	@JoinColumn(name = "userId")
	@ManyToOne(fetch = FetchType.EAGER) // 이미지를 select하면 조인해서 User정보를 같이 들고옴
	private User user; // 1, 1

	// 이미지 좋아요
	@JsonIgnoreProperties({ "image" })
	@OneToMany(mappedBy = "image")
	private List<Likes> likes;

	// 댓글 기능

	private LocalDateTime createDate; // DB에는 항상 시간이 필요함. 언제 DB가 들어왔는지 알아야 하니까...

	@Transient // DB에 컬럼이 만들어지지 않는다.
	private boolean likeState;

	@Transient
	private int likeCount;
	
	@PrePersist
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}

//	오브젝트를 콘솔에 출력할 때 문제가 될 수 있어서 User 부분을 출력되지 않게 함.
//	@Override
//	public String toString() {
//		return "Image [id=" + id + ", caption=" + caption + ", postImageUrl=" + postImageUrl 
//				+ ", createDate=" + createDate + "]";
//	}

}
