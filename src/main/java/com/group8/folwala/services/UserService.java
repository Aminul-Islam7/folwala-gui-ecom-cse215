package com.group8.folwala.services;

import com.group8.folwala.models.User;

import javafx.scene.Scene;

import java.io.*;
import java.util.ArrayList;

public class UserService {
  private ArrayList<User> users;
  private User currentUser;
  private static final String USER_FILE_PATH = "data/users.txt";
  private static final String SESSION_FILE_PATH = "data/session.txt";

  public UserService() {
    users = loadUsers();
    currentUser = loadSession();
  }

  public boolean registerUser(String name, String phone, String password, String address, boolean isAdmin) {
    if (getUserByPhone(phone) != null) {
      return false; // User with this phone number already exists
    }
    User newUser = new User(name, phone, password, address, isAdmin);
    users.add(newUser);
    saveUser(newUser);
    return true;
  }

  public User loginUser(String phone, String password) {
    User user = getUserByPhone(phone);
    if (user != null && user.validatePassword(password)) {
      currentUser = user;
      saveSession();
      return user;
    }
    return null;
  }

  public void logoutUser() {
    currentUser = null;
    clearSession();
  }

  public User getCurrentUser() {
    return currentUser;
  }

  private User getUserByPhone(String phone) {
    for (User user : users) {
      if (user.getPhone().equals(phone)) {
        return user;
      }
    }
    return null;
  }

  private ArrayList<User> loadUsers() {
    ArrayList<User> users = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(USER_FILE_PATH))) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] userDetails = line.split(":");
        if (userDetails.length == 5) {
          String name = userDetails[0];
          String phone = userDetails[1];
          String password = userDetails[2];
          String address = userDetails[3];
          boolean isAdmin = Boolean.parseBoolean(userDetails[4]);
          users.add(new User(name, phone, password, address, isAdmin));
        }
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
    return users;
  }

  private void saveUser(User user) {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(USER_FILE_PATH, true))) {
      bw.write(user.getName() + ":" + user.getPhone() + ":" + user.getPassword() + ":" + user.getAddress() + ":"
          + user.isAdmin());
      bw.newLine();
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  private void saveSession() {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(SESSION_FILE_PATH))) {
      if (currentUser != null) {
        bw.write(currentUser.getPhone());
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  private User loadSession() {
    try (BufferedReader br = new BufferedReader(new FileReader(SESSION_FILE_PATH))) {
      String phone = br.readLine();
      return getUserByPhone(phone);
    } catch (IOException e) {
      return null;
    }
  }

  private void clearSession() {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(SESSION_FILE_PATH))) {
      bw.write("");
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }
}
