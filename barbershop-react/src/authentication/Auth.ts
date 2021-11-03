class Auth {
  private _authenticatedAsAdmin = false;

  private _authenticatedAsEmployee = false;

  private _authenticatedAsCustomer = false;

  constructor() {
    this.authenticatedAsAdmin = false;
    this.authenticatedAsEmployee = false;
    this.authenticatedAsCustomer = false;
  }

  private get authenticatedAsAdmin(): boolean {
    return this.authenticatedAsAdmin;
  }

  private set authenticatedAsAdmin(value: boolean) {
    this.authenticatedAsAdmin = value;
  }

  private get authenticatedAsEmployee(): boolean {
    return this.authenticatedAsEmployee;
  }

  private set authenticatedAsEmployee(value) {
    this.authenticatedAsEmployee = value;
  }

  private get authenticatedAsCustomer(): boolean {
    return this.authenticatedAsCustomer;
  }

  private set authenticatedAsCustomer(value) {
    this.authenticatedAsCustomer = value;
  }

  loginAsAdmin = (): void => {
    this.authenticatedAsAdmin = true;
  };

  loginAsEmploee = (): void => {
    this.authenticatedAsEmployee = true;
  };

  loginAsCustomer = (): void => {
    this.authenticatedAsCustomer = true;
  };

  logoutAsAdmin = (): void => {
    this.authenticatedAsAdmin = false;
  };

  logoutAsEmploee = (): void => {
    this.authenticatedAsEmployee = false;
  };

  logoutAsCustomer = (): void => {
    this.authenticatedAsCustomer = false;
  };
}

export default new Auth();
