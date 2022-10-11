drop database if exists APIc_spotify_playlist;
create database APIc_spotify_playlist;
use APIc_spotify_playlist;

create table app_user(
	user_id 		int primary key auto_increment,
    first_name		varchar(100) not null,
    last_name		varchar(100) not null,
    username		varchar(100) not null unique,
    passwordhash	char(100) not null,
    email			varchar(200) not null unique,
    isDeleted		boolean default 0
);

create table app_role(
	role_id		int primary key auto_increment,
    `name`		varchar(50) not null
);

create table playlist(
	playlist_id		int primary key auto_increment,
    `name`			varchar(100) not null,
    `description`		varchar(1000),
    
    user_id			int not null,
    constraint fk_playlist_user foreign key (user_id) references app_user(user_id)
);

create table track(
	track_id		int primary key auto_increment,
    `name`			varchar(250) not null,
    duration_ms		bigint not null
);

create table artist(
	artist_id		int primary key auto_increment,
    `name`			varchar(250) not null
);

create table tag(
	tag_id 			int primary key auto_increment,
    content			varchar(250) not null,
    
    user_id			int not null,
    constraint fk_tag_user foreign key (user_id) references app_user(user_id)
);

create table image(
	image_id		int primary key auto_increment,
    url				varchar(250) not null,
    height			int not null,
    width			int not null
);

create table user_role(
	user_id		int not null,
    role_id		int not null,
    
    constraint pk_user_role primary key (user_id, role_id),
    constraint fk_user_role_user foreign key (user_id) references app_user(user_id),
    constraint fk_user_role_role foreign key (role_id) references app_role(role_id)
);

create table user_playlist(
	user_id			int not null,
    playlist_id		int not null,
    
    accepted	boolean default 0,
    
    constraint pk_user_playlist primary key (user_id, playlist_id),
    constraint fk_user_playlist_user foreign key (user_id) references app_user(user_id),
    constraint fk_user_playlist_playlist foreign key (playlist_id) references playlist(playlist_id)
);

create table track_playlist(
	track_id		int not null,
    playlist_id		int not null,
    
    user_id			int not null,
    constraint fk_user_track_playlist foreign key (user_id) references app_user(user_id),
    
    constraint pk_track_playlist primary key (track_id, playlist_id),
    constraint fk_track_playlist_track foreign key (track_id) references track(track_id),
    constraint fk_track_playlist_playlist foreign key (playlist_id) references playlist(playlist_id)
);

create table tag_playlist(
	tag_id			int not null,
    playlist_id		int not null,
    
    constraint pk_tag_playlist primary key (tag_id, playlist_id),
    constraint fk_tag_playlist_tag foreign key (tag_id) references tag(tag_id),
    constraint fk_tag_playlist_playlist foreign key (playlist_id) references playlist(playlist_id)
);

create table image_playlist(
	image_id		int not null,
    playlist_id		int not null,
    
    constraint pk_image_playlist primary key (image_id, playlist_id),
    constraint fk_image_playlist_image foreign key (image_id) references image(image_id),
    constraint fk_image_playlist_playlist foreign key (playlist_id) references playlist(playlist_id)
);

create table track_artist(
	track_id		int not null,
    artist_id		int not null,
    
    constraint pk_track_artist primary key (track_id, artist_id),
    constraint fk_track_artist_track foreign key (track_id) references track(track_id),
    constraint fk_track_artist_artist foreign key (artist_id) references artist(artist_id)
);