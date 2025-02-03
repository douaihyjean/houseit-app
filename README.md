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

### Home Page
![Home Page](screenshots/HomePage.png)

### Create Account
![Create Account](screenshots/CreateAccount.png)

### Price Prediction
![Price Prediction](screenshots/PricePrediction.png)

### Search Page
![Search Page](screenshots/SearchPage.png)

### Property Details
![Property Details](screenshots/PropertyDetails.png)

---

## 👥 Contributors
- Nour Safa
- Jean Douaihy
- Riwa Nahas

---

## 📧 Contact
For questions or collaborations, please contact us at **jeandouaihy2003@gmail.com**.
