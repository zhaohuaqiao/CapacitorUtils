declare module "@capacitor/core" {
  interface PluginRegistry {
    CapacitorUtils: CapacitorUtilsPlugin;
  }
}

export interface CapacitorUtilsPlugin {
  echo(options: { value: string }): Promise<{value: string}>;
}
