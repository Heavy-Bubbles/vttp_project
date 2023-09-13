export interface Customer{
    id: number
    name: string
    gender: string
    phone: string
    email: string
}

export interface Services{
    id: number
    name: string
    durationInMinutes: number
    price: number
}

export interface Appointment{
    id: string
    appointmentStart: number
    appointmentEnd: number
    customerId: number
}

export interface AppointmentFormData{
    appointmentStart: number
    customer: number
    services: number[]
}

export interface AppointmentDetails{
    appointmentId: string
    serviceId: number
}

export interface Invoice{
    id: number
    appointmentId: string
    amountDue: number
    invoiceDate: number
    url: string
}
