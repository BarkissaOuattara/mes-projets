export enum LogType {
  CREATE = 'CREATE',
  READ = 'READ',
  UPDATE = 'UPDATE',
  DELETE = 'DELETE',
  LOGIN = 'LOGIN',
  LOGOUT = 'LOGOUT',
  RESILIATION = 'RESILIATION',
  ASSIGNMENT = 'ASSIGNMENT',
  OTHER = 'OTHER'
}

export enum EntityName {
  ABONNE = 'ABONNE',
  ABONNEMENT = 'ABONNEMENT',
  BRANCHEMENT = 'BRANCHEMENT',
  EXPLOITATION = 'EXPLOITATION',
  TYPE_CLIENT = 'TYPE_CLIENT',
  TYPE_BRANCHEMENT = 'TYPE_BRANCHEMENT',
  TARIF = 'TARIF',
  AUTRE = 'AUTRE'
}

export interface AppLogs {
  id?: number;
  dateTime: Date;
  logType: LogType;
  entityName: EntityName;
  authorCode: string;
  codeObject: string;
  message: string;
}
