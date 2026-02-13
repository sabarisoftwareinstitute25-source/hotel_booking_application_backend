-- Create hotel_vendors table for full registration form data
-- This table stores all data from the 5-step registration form

CREATE TABLE IF NOT EXISTS hotel_vendors (
    registration_id VARCHAR(50) PRIMARY KEY,
    hotel_id VARCHAR(20) NOT NULL,
    property_type VARCHAR(20) DEFAULT 'Hotel',
    
    -- Step 1: Basic Details
    hotel_name VARCHAR(150) NOT NULL,
    hotel_type VARCHAR(50),
    year_of_establishment VARCHAR(4),
    total_rooms VARCHAR(10),
    owner_name VARCHAR(100) NOT NULL,
    mobile_number VARCHAR(20) NOT NULL,
    alternate_contact VARCHAR(20),
    landline_numbers JSONB,
    email VARCHAR(150),
    website VARCHAR(255),
    
    -- Step 2: Address
    address_line1 VARCHAR(255) NOT NULL,
    address_line2 VARCHAR(255),
    city VARCHAR(100) NOT NULL,
    district VARCHAR(100),
    state VARCHAR(100) NOT NULL,
    pin_code VARCHAR(6) NOT NULL,
    landmark VARCHAR(255),
    
    -- Step 3: Room Details
    selected_room_types JSONB,
    room_details JSONB,
    min_tariff VARCHAR(20),
    max_tariff VARCHAR(20),
    extra_bed_available BOOLEAN,
    
    -- Step 4: Amenities
    basic_amenities JSONB,
    hotel_facilities JSONB,
    food_services JSONB,
    additional_amenities JSONB,
    custom_amenities JSONB,
    
    -- Step 5: Legal Documents
    aadhar_number VARCHAR(12),
    gst_number VARCHAR(15),
    fssai_license VARCHAR(14),
    pan_number VARCHAR(10),
    person_photo_info JSONB,
    uploaded_files JSONB,
    
    -- Status and timestamps
    registration_status VARCHAR(20) DEFAULT 'PENDING',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    -- Indexes for faster lookups
    CONSTRAINT unique_registration_id UNIQUE (registration_id),
    CONSTRAINT unique_email UNIQUE (email),
    CONSTRAINT unique_mobile_number UNIQUE (mobile_number)
);

-- Create indexes for faster searches
CREATE INDEX IF NOT EXISTS idx_hotel_vendors_email ON hotel_vendors(email);
CREATE INDEX IF NOT EXISTS idx_hotel_vendors_mobile ON hotel_vendors(mobile_number);
CREATE INDEX IF NOT EXISTS idx_hotel_vendors_city ON hotel_vendors(city);
CREATE INDEX IF NOT EXISTS idx_hotel_vendors_state ON hotel_vendors(state);
CREATE INDEX IF NOT EXISTS idx_hotel_vendors_status ON hotel_vendors(registration_status);
CREATE INDEX IF NOT EXISTS idx_hotel_vendors_property_type ON hotel_vendors(property_type);

