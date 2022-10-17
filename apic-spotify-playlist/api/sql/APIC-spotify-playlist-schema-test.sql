drop database if exists APIc_spotify_playlist_test;
create database APIc_spotify_playlist_test;
use APIc_spotify_playlist_test;

create table app_user(
	app_user_id 	int primary key auto_increment,
    first_name		varchar(100) not null,
    last_name		varchar(100) not null,
    username		varchar(100) not null unique,
    password_hash	char(100) not null,
    email			varchar(200) not null unique,
    disabled		boolean default 0
);

create table app_role(
	app_role_id		int primary key auto_increment,
    `name`			varchar(50) not null
);

create table playlist(
	playlist_id		int primary key auto_increment,
    `name`			varchar(100) not null,
    `description`		varchar(1000),
    
    app_user_id			int not null,
    constraint fk_playlist_user foreign key (app_user_id) references app_user(app_user_id)
);

create table track(
	track_id		int primary key auto_increment,
    track_name		varchar(250) not null,
    duration_ms		bigint not null,
    artist_name		varchar(250) not null,
    app_user_id		int not null,
    playlist_id		int not null,
    
    constraint fk_track_user foreign key (app_user_id) references app_user(app_user_id),
    constraint fk_track_playlist foreign key (playlist_id) references playlist(playlist_id)
);

create table tag(
	tag_id 			int primary key auto_increment,
    content			varchar(250) not null,
    app_user_id		int not null,
    playlist_id		int not null,
    
    constraint fk_tag_user foreign key (app_user_id) references app_user(app_user_id),
	constraint fk_tag_playlist foreign key (playlist_id) references playlist(playlist_id)

);

create table image(
	image_id		int primary key auto_increment,
    url				varchar(250) not null,
    height			int not null,
    width			int not null,
    playlist_id 	int not null,
    
	constraint fk_image_playlist foreign key (playlist_id) references playlist(playlist_id)
);

create table user_role(
	app_user_id		int not null,
    app_role_id			int not null,
    
    constraint pk_user_role primary key (app_user_id, app_role_id),
    constraint fk_user_role_user foreign key (app_user_id) references app_user(app_user_id),
    constraint fk_user_role_role foreign key (app_role_id) references app_role(app_role_id)
);

create table user_playlist(
	app_user_id		int not null,
    playlist_id		int not null,
    
    accepted	boolean default 0,
    
    constraint pk_user_playlist primary key (app_user_id, playlist_id),
    constraint fk_user_playlist_user foreign key (app_user_id) references app_user(app_user_id),
    constraint fk_user_playlist_playlist foreign key (playlist_id) references playlist(playlist_id)
);

delimiter //
create procedure set_good_known_state()
begin

    delete from user_playlist;
    delete from user_role;
    delete from image;
    alter table image auto_increment = 1;
    delete from tag;
    alter table tag auto_increment = 1;
    delete from artist;
    alter table artist auto_increment = 1;
    delete from track;
    alter table track auto_increment = 1;
    delete from playlist;
    alter table playlist auto_increment = 1;
    delete from app_role;
    alter table app_role auto_increment = 1;
    delete from app_user;
    alter table app_user auto_increment = 1;
    
    insert into app_user (app_user_id, first_name, last_name, username, password_hash, email, disabled) values
		('1', 'Alice', 'Ayers', 'AAyers', '$2a$10$FWWWxxj3ohyWsPQxyTpPGe05HohbzHBZUW9eeDWHAw7GjCEmzV.cu', 'aayers@apple.com', 0),
        ('2', 'Bob', 'Bobberson', 'BBobers', '$2a$10$FWWWxxj3ohyWsPQxyTpPGe05HohbzHBZUW9eeDWHAw7GjCEmzV.cu', 'bbobberson@bob.bob', 0),
        ('3', 'Clyde', 'Clemens', 'CClems', '$2a$10$FWWWxxj3ohyWsPQxyTpPGe05HohbzHBZUW9eeDWHAw7GjCEmzV.cu', 'cclemens@clementine.net', 0),
        ('4', 'Danielle', 'Davison', 'DDavis', '$2a$10$FWWWxxj3ohyWsPQxyTpPGe05HohbzHBZUW9eeDWHAw7GjCEmzV.cu', 'ddavison@dmail.web', 0);
        
	insert into app_role (app_role_id, `name`) values
		('1', 'USER'),
        ('2', 'GROUP-ADMIN'),
        ('3', 'APP-ADMIN');
        
	insert into playlist (playlist_id, `name`, `description`, app_user_id) values
		('1', 'Jazzy jazz', 'Smooth, classic, and always fresh', '1'),
        ('2', 'Jam rock', 'A fusion of rock and long-winded jam sessions', '2'),
        ('3', 'lo-fi hip-hop', 'music to study to', '3'),
        ('4', 'workout', 'gotta get that pump', '4');
    
    insert into track (track_id, track_name, duration_ms, artist_name, app_user_id, playlist_id) values
		('1', 'Bird Food', '331000', 'Ornet Coleman', '1', '1'),
        ('2', 'Self-Portrait in Three Colours', '187000' 'Charles Mingus', '2', '2'),
        ('3', 'So What', '561000', 'Miles Davis', '3', '3'),
        ('4', 'Peaceful', '1083000', 'Eric Clapton', '4', '4'),
        ('5', 'In the Air Tonight', '336000', 'Phil Collins', '1', '4'),
        ('6', 'Friend of the Devil', '205000', 'Grateful Dead', '4', '1'),
        ('7', 'Smooth (feat. Rob Thomas)', '296000', 'Santana', '2', '3'),
        ('8', 'Hungersite', '427000', 'Goose', '3', '2'),
        ('9', "Don't Lose Site", '208000', 'Lawrence', '2', '1');
    
    insert into tag (tag_id, content, app_user_id, playlist_id) values
		('1', 'Jazz', '1'),
        ('2', 'Good vibes', '2'),
        ('3', 'Rockin', '3'),
        ('4', 'Chill', '4'),
        ('5', 'Upbeat', '1'),
        ('6', 'Lowkey', '2'),
        ('7', 'Party', '3'),
        ('8', 'Groovy', '4');
    
    insert into image (image_id, url, height, width, playlist_id) values
		('1', 'https://placekitten.com/300/300', '300', '300', '1'),
		('2', 'https://placekitten.com/300/300', '300', '300', '2'),
		('2', 'https://placekitten.com/300/300', '300', '300', '3'),
		('2', 'https://placekitten.com/300/300', '300', '300', '4');

    
    insert into user_role (app_user_id, app_role_id) values
		('1', '1'),
        ('1', '2'),
        ('2', '1'),
        ('2', '2'),
        ('3', '1'),
        ('4', '3');

	insert into user_playlist (app_user_id, playlist_id, accepted) values
		('1', '1', '1'),
        ('1', '2', '1'),
        ('2', '2', '1'),
        ('2', '1', '0'),
        ('3', '2', '1');
        
end //
delimiter ;
