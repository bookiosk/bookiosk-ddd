package org.bookiosk.ddd.domain;

/**
 * Immutable 3D coordinate that uniquely identifies a business scenario.
 * Used as a routing key in the extension mechanism to dispatch the correct
 * {@link ExtensionPointI} implementation at runtime.
 *
 * The three dimensions are:
 *   bizId   — business identity (e.g. "tmall", "taobao")
 *   useCase — use case (e.g. "placeOrder")
 *   scenario — scenario (e.g. "88vip", "normal")
 *
 * All dimensions default to {@code "default"} when not specified.
 */
public final class BizScenario {

    public static final String DEFAULT_BIZ_ID   = "default";
    public static final String DEFAULT_USE_CASE = "default";
    public static final String DEFAULT_SCENARIO = "default";

    private final String bizId;
    private final String useCase;
    private final String scenario;

    private BizScenario(String bizId, String useCase, String scenario) {
        this.bizId = bizId;
        this.useCase = useCase;
        this.scenario = scenario;
    }

    public static BizScenario of(String bizId, String useCase, String scenario) {
        return new BizScenario(bizId, useCase, scenario);
    }

    public static BizScenario of(String bizId, String useCase) {
        return new BizScenario(bizId, useCase, DEFAULT_SCENARIO);
    }

    public static BizScenario defaultBiz() {
        return new BizScenario(DEFAULT_BIZ_ID, DEFAULT_USE_CASE, DEFAULT_SCENARIO);
    }

    public String getBizId() { return bizId; }
    public String getUseCase() { return useCase; }
    public String getScenario() { return scenario; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BizScenario)) return false;
        BizScenario that = (BizScenario) o;
        return bizId.equals(that.bizId)
            && useCase.equals(that.useCase)
            && scenario.equals(that.scenario);
    }

    @Override
    public int hashCode() {
        int result = bizId.hashCode();
        result = 31 * result + useCase.hashCode();
        result = 31 * result + scenario.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "BizScenario{bizId='" + bizId
            + "', useCase='" + useCase
            + "', scenario='" + scenario + "'}";
    }
}
