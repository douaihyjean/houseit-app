# HouseIt: Real Estate Marketplace

## 📌 Overview
HouseIt is a real estate marketplace application designed to provide users with the ability to list properties, search and filter listings, and access AI-powered house price predictions. This platform is designed to help buyers and sellers make informed decisions confidently.

## 🎯 Objectives
1. Create a marketplace for real estate listings.
2. Provide **AI-powered house price predictions** for better decision-making.
3. Offer seamless user account management, property listing, and search features.

---

## 🚀 Features

### User Features
1. **Create Account**: Users can register securely with email and password validation.
2. **Login**: Authenticate with email and password.
3. **Price Prediction**: AI-powered property price predictions based on user-provided details.
4. **Add Listings**: Add new property listings, including title, price, address, area, bedrooms, bathrooms, and images.
5. **Edit Listings**: Update property details and save changes to the database.
6. **Search and Filter**:
   - Search by area, price, bedrooms, bathrooms, etc.
   - View property cards displaying price, location, and images.
7. **Saved Properties**: Mark properties as favorites to save them for later viewing.
8. **Property Details**: View detailed information about properties.

### Developer Features
1. **Firebase Authentication**:
   - Secure user registration and login.
   - Real-time synchronization of user data with Firebase backend.
   - Dashboard for user management (add, update, delete users).
2. **Machine Learning Integration**:
   - A prediction model built using `LinearRegression` from scikit-learn.
   - Flask API to handle property price prediction requests.
3. **Database Management**:
   - **SQLite** for local data storage.
   - **SQLAlchemy** for CRUD operations.
   - Models and relationships defined in `models.py`.
   - Pydantic schemas for data validation.
4. **Backend API**:
   - Developed using **FastAPI**.
   - CORS middleware for cross-platform access.
5. **Kotlin Frontend**:
   - `ListingsApiService.kt`: Handles server communication for property CRUD operations.
   - `RetrofitInstance.kt`: Configures network calls and JSON data conversion.
   - `CreateAccountActivity.kt`: Manages user account creation.

---

## 🛠 Tech Stack

| Component            | Technology                          |
|----------------------|-------------------------------------|
| **Frontend**         | Kotlin                              |
| **Backend**          | Python, FastAPI                    |
| **Database**         | SQLite, SQLAlchemy                 |
| **Machine Learning** | scikit-learn                       |
| **Authentication**   | Firebase Authentication            |
| **API Integration**  | Flask, FastAPI, Retrofit           |
| **Other Libraries**  | Pydantic, RecyclerView             |

---

## 📁 Project Structure

```
/app
  ├── activities
  │   ├── CreateAccountActivity.kt
  │   ├── LoginActivity.kt
  │   └── PropertyDetailsActivity.kt
  ├── adapters
  │   └── PropertyAdapter.kt
  ├── api
  │   ├── ListingsApiService.kt
  │   └── RetrofitInstance.kt
  ├── database
  │   └── DatabaseHelper.kt
  └── models
      └── Property.kt

/backend
  ├── main.py          # FastAPI entry point
  ├── database.py      # SQLite connection setup
  ├── models.py        # SQLAlchemy models
  ├── schemas.py       # Pydantic schemas
  └── crud.py          # Database CRUD operations

/machine_learning
  ├── train_model.py   # Training the prediction model
  └── predict.py       # Flask API for predictions

/screenshots          # App screenshots
```

---

## 🔧 Installation & Setup

### Backend Setup
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/HouseIt.git
   cd HouseIt/backend
   ```

2. Install Python dependencies:
   ```bash
   pip install -r requirements.txt
   ```

3. Run the FastAPI backend:
   ```bash
   uvicorn main:app --reload
   ```

### Machine Learning API Setup
1. Navigate to the `machine_learning` directory:
   ```bash
   cd ../machine_learning
   ```

2. Run the Flask app:
   ```bash
   python predict.py
   ```

### Android Frontend Setup
1. Open the project in Android Studio.
2. Sync Gradle and resolve dependencies.
3. Build and run the app on an emulator or device.

---

## 🎨 Screenshots

![Screenshot 2024-12-07 141406](https://github.com/user-attachments/assets/2cd5148c-c46d-4f29-8dee-b12a13e1cffe)

![Screenshot 2024-12-07 141651](https://github.com/user-attachments/assets/9ba9de3d-5bba-4af5-b016-247ad14c0f86)

![Screenshot 2024-12-07 141719](https://github.com/user-attachments/assets/ccb179d9-da14-43cc-8459-b8c2cae4a846)

![Screenshot 2024-12-07 141739](https://github.com/user-attachments/assets/b83ab81d-a3b7-45e4-a3eb-b7462bbe33a0)

![Screenshot 2024-12-07 141901](https://github.com/user-attachments/assets/e21db509-ebb9-496b-ac6f-b530c05667cb)

![Screenshot 2024-12-07 141922](https://github.com/user-attachments/assets/7d07712e-f1f0-4843-b2a3-6f08618045f5)

![Screenshot 2024-12-07 141943](https://github.com/user-attachments/assets/a5ca61a9-bc6b-4a0d-88d1-c0334998d9d7)

![Screenshot 2024-12-07 142012](https://github.com/user-attachments/assets/f9452a78-93af-46d1-8b86-9795c175cf66)

![Screenshot 2024-12-07 142140](https://github.com/user-attachments/assets/5b1a9ff7-73ee-45e8-8c18-60fff03f19d9)

![Screenshot 2024-12-07 142206](https://github.com/user-attachments/assets/c98c302c-0c73-42cd-8c5c-e9b520c6323a)

![Screenshot 2024-12-07 142236](https://github.com/user-attachments/assets/20abc2f4-0bf6-4ba4-bad4-feef335a0e90)

![Screenshot 2024-12-07 142402](https://github.com/user-attachments/assets/de7edf52-d877-481d-ab73-921abf1e2342)

![Screenshot 2024-12-07 142420](https://github.com/user-attachments/assets/6a9888de-d030-48a1-8af3-9aaf6f2e947b)

![Screenshot 2024-12-07 142517](https://github.com/user-attachments/assets/155eca3c-2934-4fa1-b275-25b489ccd6ac)

![Screenshot 2024-12-07 142546](https://github.com/user-attachments/assets/27cb48df-d1bb-4917-b4eb-393720e97287)

![Screenshot 2024-12-07 142602](https://github.com/user-attachments/assets/c2c124b2-be3d-4bba-abda-305d5bc47c7e)

![Screenshot 2024-12-07 142627](https://github.com/user-attachments/assets/782fd666-2b27-42ef-a19e-6cf146e4342b)


---

## 👥 Contributors
- Nour Safa
- Jean Douaihy
- Riwa Nahas

---

## 📧 Contact
For questions or collaborations, please contact us at **jeandouaihy2003@gmail.com**.
