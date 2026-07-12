package org.bookiosk.ddd.domain;

/**
 * Internal map key associating an {@link ExtensionPointI} sub-interface and a
 * {@link BizScenario} to a concrete implementation class.
 * Package-private — used only inside {@link ExtensionRepository}.
 */
final class ExtensionCoordinate {

    private final Class<?> extensionPtClz;
    private final BizScenario bizScenario;

    ExtensionCoordinate(Class<?> extensionPtClz, BizScenario bizScenario) {
        if (extensionPtClz == null) {
            throw new IllegalArgumentException("extensionPtClz must not be null");
        }
        if (bizScenario == null) {
            throw new IllegalArgumentException("bizScenario must not be null");
        }
        this.extensionPtClz = extensionPtClz;
        this.bizScenario = bizScenario;
    }

    Class<?> getExtensionPtClz() { return extensionPtClz; }
    BizScenario getBizScenario() { return bizScenario; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExtensionCoordinate)) return false;
        ExtensionCoordinate that = (ExtensionCoordinate) o;
        return extensionPtClz.equals(that.extensionPtClz)
            && bizScenario.equals(that.bizScenario);
    }

    @Override
    public int hashCode() {
        int result = extensionPtClz.hashCode();
        result = 31 * result + bizScenario.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ExtensionCoordinate{pt=" + extensionPtClz.getSimpleName()
            + ", scenario=" + bizScenario + "}";
    }
}
