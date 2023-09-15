CREATE TABLE IF NOT EXISTS tariff
(
    id                                   BIGINT PRIMARY KEY,
    title                                VARCHAR(255),
    description                          VARCHAR(1024),
    data_package_gb                      INT,
    minutes_package                      INT,
    internet_speed_mbps                  INT,
    tv_channels                          INT,
    subscription_fee                     INT,
    discount_fee                         INT,
    subscription_fee_annotation_settings VARCHAR(1024),
    benefits_description                 VARCHAR(1024),
    link                                 VARCHAR(1024)
);