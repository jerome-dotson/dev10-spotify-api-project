//package learn.spotifyPlaylist.controllers;
//
//import org.springframework.dao.DataAccessException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.converter.HttpMessageNotReadableException;
//import org.springframework.web.HttpMediaTypeNotSupportedException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(DataAccessException.class)
//    public ResponseEntity<Object> handleDataException(DataAccessException toHandle) {
//        return new ResponseEntity<>(toHandle.getMessage(), HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(HttpMessageNotReadableException.class)
//    public ResponseEntity<Object> handleException(HttpMessageNotReadableException toHandle) {
//        return new ResponseEntity<>("Sorry we could not read your http message", HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
//    public ResponseEntity<Object> handleException(HttpMediaTypeNotSupportedException toHandle) {
//        return new ResponseEntity<>("Sorry you're media type is messed up", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
//    }
//
//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<Object> handleDataException(IllegalArgumentException toHandle) {
//        return new ResponseEntity<>(toHandle.getMessage(), HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> handleGenericException(Exception toHandle) {
//        return new ResponseEntity<>("Something went wrong on our end, sorry!", HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//}

