package com.alloymobile.client.persistence.querydsl;

import com.alloymobile.client.persistence.model.Address;
import com.alloymobile.client.persistence.model.AddressType;
import com.alloymobile.client.persistence.model.Location;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.BeanPath;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.SimplePath;
import com.querydsl.core.types.dsl.StringPath;

import javax.annotation.processing.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QAddress is a Querydsl query type for Address
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QAddress extends BeanPath<Address> {

    private static final long serialVersionUID = 935116299L;

    public static final QAddress address1 = new QAddress("address1");

    public final StringPath address = createString("address");

    public final StringPath city = createString("city");

    public final StringPath country = createString("country");

    public final StringPath id = createString("id");

    public final SimplePath<Location> location = createSimple("location", Location.class);

    public final StringPath postalCode = createString("postalCode");

    public final StringPath state = createString("state");

    public final EnumPath<AddressType> type = createEnum("type", AddressType.class);

    public QAddress(String variable) {
        super(Address.class, forVariable(variable));
    }

    public QAddress(Path<? extends Address> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAddress(PathMetadata metadata) {
        super(Address.class, metadata);
    }

}

