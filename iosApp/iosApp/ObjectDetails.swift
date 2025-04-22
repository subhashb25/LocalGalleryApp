import SwiftUI
import KMPNativeCoroutinesAsync
import shared

class DetailViewModelWrapper: ObservableObject {
    private let viewModel: DetailViewModel
    @Published var museumObject: MuseumObject?

    init(objectId: Int32) {
        self.viewModel = DetailViewModel(museumRepository: KoinDependencies().museumRepository)
        viewModel.setId(objectId: objectId)
        observeMuseumObject()
    }

    private func observeMuseumObject() {
        Task {
            do {
                // Observe the Kotlin flow and update the museumObject
                for try await result in asyncSequence(for: viewModel.museumObject) as AsyncThrowingStream<MuseumObject, Error> {
                    await MainActor.run {
                        self.museumObject = result
                    }
                }
            } catch {
                print("Error observing museum object: \(error)")
            }
        }
    }
}

struct DetailView: View {
    @StateObject private var viewModel: DetailViewModelWrapper

    init(objectId: Int32) {
        _viewModel = StateObject(wrappedValue: DetailViewModelWrapper(objectId: objectId))
    }

    var body: some View {
        VStack {
            if let obj = viewModel.museumObject {
                ObjectDetails(obj: obj)
            } else {
                ProgressView("Loading...")
            }
        }
        .onAppear {
            // Ensure that we trigger the observation when the view appears
            viewModel.observeMuseumObject()
        }
    }
}

struct ObjectDetails: View {
    var obj: MuseumObject

    var body: some View {
        ScrollView {
            VStack {
                // AsyncImage to load image from URL
                AsyncImage(url: URL(string: obj.primaryImageSmall)) { phase in
                    switch phase {
                    case .empty:
                        ProgressView()
                            .frame(maxWidth: .infinity, maxHeight: .infinity)
                    case .success(let image):
                        image
                            .resizable()
                            .scaledToFill()
                            .clipped()
                    default:
                        EmptyView()
                    }
                }
                .frame(maxWidth: .infinity, maxHeight: 300)

                VStack(alignment: .leading, spacing: 6) {
                    Text(obj.title)
                        .font(.title)

                    LabeledInfo(label: "Artist", data: obj.artistDisplayName)
                    LabeledInfo(label: "Date", data: obj.objectDate)
                    LabeledInfo(label: "Dimensions", data: obj.dimensions)
                    LabeledInfo(label: "Medium", data: obj.medium)
                    LabeledInfo(label: "Department", data: obj.department)
                    LabeledInfo(label: "Repository", data: obj.repository)
                    LabeledInfo(label: "Credits", data: obj.creditLine)
                }
                .padding(16)
            }
        }
    }
}

struct LabeledInfo: View {
    var label: String
    var data: String

    var body: some View {
        HStack {
            Text("\(label):")
                .fontWeight(.bold)
            Text(data)
        }
        .padding(.bottom, 4)
    }
}
