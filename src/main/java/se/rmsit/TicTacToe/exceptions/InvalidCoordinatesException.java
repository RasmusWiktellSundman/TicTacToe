package se.rmsit.TicTacToe.exceptions;

// Exception för ogiltiga koordinater, det vill säga om koordinaterna ligger utanför spelplanen.
// Använder RuntimeException eftersom felet inte ska kunna ske om korrekt data skickas.
public class InvalidCoordinatesException extends RuntimeException {}
