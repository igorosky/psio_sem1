package College;

public enum Department {
    W1,
    W2,
    W3,
    W4N,
    W5,
    W6,
    W7,
    W8,
    W9,
    W10,
    W11,
    W12,
    W13,
    W14;

    // Optional would be a better thing than exception...
    public static Department getDepartment(int val) throws DepartmentNotExistException {
        switch (val) {
            case 1:
                return Department.W1;
            case 2:
                return Department.W2;
            case 3:
                return Department.W3;
            case 4:
                return Department.W4N;
            case 5:
                return Department.W5;
            case 6:
                return Department.W6;
            case 7:
                return Department.W7;
            case 8:
                return Department.W8;
            case 9:
                return Department.W9;
            case 10:
                return Department.W10;
            case 11:
                return Department.W11;
            case 12:
                return Department.W12;
            case 13:
                return Department.W13;
            case 14:
                return Department.W14;
            default:
                throw new DepartmentNotExistException();
        }
    }
}
