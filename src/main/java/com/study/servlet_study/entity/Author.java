package com.study.servlet_study.entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Author {
	private int authorId;
	private String authorName;
}
