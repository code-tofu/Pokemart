export interface AuthRequest {
  username: string;
  password: string;
}

export interface AuthResponse {
  userID: string;
  role: string;
  accessToken: string;
  refreshToken: string;
}

export interface RegisterRequest {
  //userdetails
  username: string;
  password: string;
  //userprofile
  customerName: string;
  customerEmail: string;
  customerPhone: string;
  shippingAddress: string;
  birthdate: number;
  gender: string;
}

export interface UpdateRequest {
  userID: string;
  customerEmail: string;
  customerPhone: string;
  shippingAddress: string;
  gender: string;
}

export interface UserProfile {
  userID: string;
  customerName: string;
  customerEmail: string;
  customerPhone: string;
  shippingAddress: string;
  birthdate: number;
  gender: string;
  memberLevel: string;
  memberSince: number;
}
